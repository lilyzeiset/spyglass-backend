package com.skillstorm.project.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.project.dtos.GoalDto;
import com.skillstorm.project.models.Goal;
import com.skillstorm.project.repositories.GoalRepository;

@Service
@Transactional
public class GoalService {
	
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

	public GoalDto createGoal(@Valid GoalDto goalData) {
		Goal goal = new Goal(goalData);
		return goalRepository.save(goal).toDto();
	}

	public GoalDto updateGoal(long id, @Valid GoalDto goalData) {
		Goal goal = new Goal(goalData);
		goal.setId(id);
		return goalRepository.save(goal).toDto();
	}

	public void deleteGoal(long id) {
		goalRepository.deleteById(id);
	}

}
