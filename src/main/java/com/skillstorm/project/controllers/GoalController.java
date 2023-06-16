package com.skillstorm.project.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.project.dtos.GoalDto;
import com.skillstorm.project.services.GoalService;

@RestController
@RequestMapping("/goal")
@CrossOrigin
public class GoalController {
	
	@Autowired
	private GoalService goalService;
	
	@GetMapping
	public List<GoalDto> getAllGoals(){
		return goalService.getAllGoals();
	}
	
	@GetMapping("/{id}")
	public GoalDto getGoalById(@PathVariable long id) {
		return goalService.getGoalById(id);
	}
	
	@PostMapping
	public ResponseEntity<GoalDto> createGoal(@Valid @RequestBody GoalDto goalData) {
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
