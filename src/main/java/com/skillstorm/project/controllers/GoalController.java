package com.skillstorm.project.controllers;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.skillstorm.project.dtos.GoalDto;
import com.skillstorm.project.services.GoalService;

@RestController
@RequestMapping("/goal")
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://localhost:5173","http://lily-spyglass.s3-website-us-east-1.amazonaws.com*","http://lily-spyglass-env.eba-he3agp52.us-east-1.elasticbeanstalk.com*"})
public class GoalController {
	
	@Autowired
	private GoalService goalService;
	
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
	  public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable long id) {
		if (!image.isEmpty()) {
		      System.out.println("Received image");
	      //upload to s3, update goal.imageUrl
	      return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully!");
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No image file provided.");
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
