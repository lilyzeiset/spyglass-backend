package com.skillstorm.project.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.project.dtos.GoalDto;
import com.skillstorm.project.models.AppUser;
import com.skillstorm.project.models.Goal;
import com.skillstorm.project.repositories.GoalRepository;
import com.skillstorm.project.repositories.UserRepository;

@Service
@Transactional
public class GoalService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GoalRepository goalRepository;

	public List<GoalDto> getAllGoals() {
		return goalRepository.findAll()
				.stream()
				.map(Goal::toDto)
				.collect(Collectors.toList());
	}

	public GoalDto getGoalById(long id) {
		Goal goal = goalRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException());
		return goal.toDto();
	}
	
	public List<GoalDto> getAllGoalsByUserId(String userId) {
		return goalRepository.findAllGoalsByUserId(userId)
				.stream()
				.map(Goal::toDto)
				.collect(Collectors.toList());
	}

	public GoalDto createGoal(@Valid GoalDto goalData) {
		Goal goal = new Goal(goalData);
//		Optional<AppUser> user = userRepository.findById(goalData.getUserId());
//		if (user.isPresent()) {
//			goal.setUser(user.get());
//		}
		return goalRepository.save(goal).toDto();
	}

	public GoalDto updateGoal(long id, @Valid GoalDto goalData) {
		Goal goal = new Goal(goalData);
		goal.setId(id);
//		Optional<AppUser> user = userRepository.findById(goalData.getUserId());
//		if (user.isPresent()) {
//			goal.setUser(user.get());
//		}
		return goalRepository.save(goal).toDto();
	}

	public void deleteGoal(long id) {
		goalRepository.deleteById(id);
	}

	

}
