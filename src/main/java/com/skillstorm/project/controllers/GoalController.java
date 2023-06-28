package com.skillstorm.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.skillstorm.project.dtos.GoalDto;
import com.skillstorm.project.services.GoalService;

@RestController
@RequestMapping("/goal")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://d1ulkr0tra4x6n.cloudfront.net","http://localhost:5173","http://lily-spyglass.s3-website-us-east-1.amazonaws.com*","http://lily-spyglass-env.eba-he3agp52.us-east-1.elasticbeanstalk.com*"})
public class GoalController {
	
	private static final Logger log = LoggerFactory.getLogger(GoalController.class);

	@Autowired
	private GoalService goalService;

	@Value("${s3-bucket-name}")
	String bucketName;
	
	@Value("${aws-access-key}")
	String awsAccessKey;
	
	@Value("${aws-secret-key}")
	String awsSecretKey;
	
	/**
	 * Gets all goals for the logged-in user
	 */
	@GetMapping
	public List<GoalDto> getGoals(@AuthenticationPrincipal OAuth2User user){
		String userId = (String) user.getAttributes().get("sub");
		log.info("Getting all goals for user: " + userId);
		return goalService.getAllGoalsByUserId(userId);
	}
	
	/**
	 * Gets active goals for the logged-in user
	 */
	@GetMapping("/active")
	public List<GoalDto> getActiveGoals(@AuthenticationPrincipal OAuth2User user){
		String userId = (String) user.getAttributes().get("sub");
		log.info("Getting active goals for user: " + userId);
		return goalService.getActiveGoalsByUserId(userId);
	}
	
	/**
	 * Gets inactive goals for the logged-in user
	 * inactive: targetAmount reached or targetDate passed
	 */
	@GetMapping("/inactive")
	public List<GoalDto> getInactiveGoals(@AuthenticationPrincipal OAuth2User user){
		String userId = (String) user.getAttributes().get("sub");
		log.info("Getting inactive goals for user: " + userId);
		return goalService.getInactiveGoalsByUserId(userId);
	}
	
	/**
	 * Gets ALL goals for ALL users
	 * only available in dev mode (see SecurityConfig)
	 */
	@GetMapping("/all")
	public List<GoalDto> getAllGoals(){
		log.info("Getting ALL goals for ALL users");
		return goalService.getAllGoals();
	}
	
	/**
	 * Gets a particular goal by its ID
	 */
	@GetMapping("/{id}")
	public GoalDto getGoalById(@PathVariable long id) {
		log.info("Getting goal with id: " + id);
		return goalService.getGoalById(id);
	}
	
	/**
	 * Receives an image, uploads it to S3, 
	 * and updates imagePath of goal with the given ID
	 */
	@PostMapping(path = "/{id}/upload", consumes = "multipart/form-data")
	public ResponseEntity<String> uploadImage(
			  @RequestParam("image") MultipartFile image, 
			  @PathVariable long id, 
			  @AuthenticationPrincipal OAuth2User user) 
	{
		log.info("Uploading image for goal with id: " + id);
		if (!image.isEmpty()) {
			
			//Get existing S3 image path from DB OR generate a random one
			log.info("upload: determining S3 path");
			GoalDto goalData = goalService.getGoalById(id);
			String existingImagePath = goalData.getImagePath();
			String s3path;
			if (existingImagePath == null) {
				//Get user id (folder in S3 bucket)
				String userId = (String) user.getAttributes().get("sub");
				String origFileName = image.getOriginalFilename();
				String extension = origFileName.substring(origFileName.lastIndexOf('.'));
				s3path = userId + "/" + UUID.randomUUID().toString() + extension;
			} else {
				s3path = existingImagePath;
			}
			log.info("upload: S3 path is: " + s3path);
			
			//Convert MultipartFile to InputStream
			log.info("upload: converting MultipartFile to InputStream");
			InputStream imageInputStream;
			try {
				imageInputStream = image.getInputStream();
			} catch (IOException e) {
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Couldn't get inputStream.\"}");
			}
			
			//Create object metadata
			log.info("upload: creating metadata");
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(image.getSize());
			
			//S3 setup
			log.info("upload: setting up S3 client");
			AWSCredentials credentials = new BasicAWSCredentials(
					  awsAccessKey, 
					  awsSecretKey
					);
			AmazonS3 s3client = AmazonS3ClientBuilder
					  .standard()
					  .withCredentials(new AWSStaticCredentialsProvider(credentials))
					  .withRegion(Regions.US_EAST_1)
					  .build();
			
			//Upload object to S3
			log.info("upload: uploading object to bucket");
			s3client.putObject(
					new PutObjectRequest(
						bucketName, 
						s3path,
						imageInputStream, 
						metadata));
			log.info("upload: upload complete");
			
			//Update imagePath
			log.info("upload: setting image path");
			goalData.setImagePath(s3path);
			goalService.updateGoal(id, goalData);
			
			return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Image uploaded successfully!\"}");
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No image file provided.\"}");
	  }
	
	/**
	 * Creates a new goal and associates it with the logged-in user
	 */
	@PostMapping
	public ResponseEntity<GoalDto> createGoal(@Valid @RequestBody GoalDto goalData, @AuthenticationPrincipal OAuth2User user) {
		log.info("Creating goal");
		String userId = (String) user.getAttributes().get("sub");
		goalData.setUserId(userId);
		GoalDto createdGoal = goalService.createGoal(goalData);
		return new ResponseEntity<>(createdGoal, HttpStatus.CREATED);
	}
	
	/**
	 * Updates goal with given ID
	 */
	@PutMapping("/{id}")
	public GoalDto updateGoal(@PathVariable long id, @Valid @RequestBody GoalDto goalData) {
		log.info("Updating goal with id: " + id);
		return goalService.updateGoal(id, goalData);
	}
	
	/**
	 * Deletes goal with given ID
	 */
	@DeleteMapping("/{id}")
	public void deleteGoal(@PathVariable long id) {
		log.info("Deleting goal with id: " + id);
		goalService.deleteGoal(id);
	}
}
