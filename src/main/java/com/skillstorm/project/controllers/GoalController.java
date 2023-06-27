package com.skillstorm.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

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
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost:5173","http://lily-spyglass.s3-website-us-east-1.amazonaws.com*","http://lily-spyglass-env.eba-he3agp52.us-east-1.elasticbeanstalk.com*"})
public class GoalController {
	
	@Autowired
	private GoalService goalService;

	@Value("${s3-bucket-name}")
	String bucketName;
	
	@Value("${aws-access-key}")
	String awsAccessKey;
	
	@Value("${aws-secret-key}")
	String awsSecretKey;
	
	@GetMapping
	public List<GoalDto> getGoals(@AuthenticationPrincipal OAuth2User user){
		String userId = (String) user.getAttributes().get("sub");
		return goalService.getAllGoalsByUserId(userId);
	}
	
	@GetMapping("/active")
	public List<GoalDto> getActiveGoals(@AuthenticationPrincipal OAuth2User user){
		String userId = (String) user.getAttributes().get("sub");
		return goalService.getActiveGoalsByUserId(userId);
	}
	
	@GetMapping("/inactive")
	public List<GoalDto> getInactiveGoals(@AuthenticationPrincipal OAuth2User user){
		String userId = (String) user.getAttributes().get("sub");
		return goalService.getInactiveGoalsByUserId(userId);
	}
	
	@GetMapping("/all")
	public List<GoalDto> getAllGoals(){
		return goalService.getAllGoals();
	}
	
	@GetMapping("/{id}")
	public GoalDto getGoalById(@PathVariable long id) {
		return goalService.getGoalById(id);
	}
	
	@PostMapping(path = "/{id}/upload", consumes = "multipart/form-data")
	public ResponseEntity<String> uploadImage(
			  @RequestParam("image") MultipartFile image, 
			  @PathVariable long id, 
			  @AuthenticationPrincipal OAuth2User user) 
	{
		
		if (!image.isEmpty()) {
			
			//Get existing S3 image path from DB OR generate a random one
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
			
			//Convert MultipartFile to InputStream
			InputStream imageInputStream;
			try {
				imageInputStream = image.getInputStream();
			} catch (IOException e) {
			    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"Couldn't get inputStream.\"}");
			}
			
			//Create object metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(image.getSize());
			
			//S3 setup
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
			s3client.putObject(
					new PutObjectRequest(
						bucketName, 
						s3path,
						imageInputStream, 
						metadata));
			
			//Update imagePath
			goalData.setImagePath(s3path);
			goalService.updateGoal(id, goalData);
			
			return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Image uploaded successfully!\"}");
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"No image file provided.\"}");
	  }
	
	@PostMapping
	public ResponseEntity<GoalDto> createGoal(@Valid @RequestBody GoalDto goalData, @AuthenticationPrincipal OAuth2User user) {
		String userId = (String) user.getAttributes().get("sub");
		goalData.setUserId(userId);
		GoalDto createdGoal = goalService.createGoal(goalData);
		return new ResponseEntity<>(createdGoal, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public GoalDto updateGoal(@PathVariable long id, @Valid @RequestBody GoalDto goalData) {
		return goalService.updateGoal(id, goalData);
	}
	
	@DeleteMapping("/{id}")
	public void deleteGoal(@PathVariable long id) {
		goalService.deleteGoal(id);
	}
}
