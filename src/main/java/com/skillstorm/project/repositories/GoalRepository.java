package com.skillstorm.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skillstorm.project.models.Goal;

public interface GoalRepository extends JpaRepository<Goal,	Long>{

	@Query("SELECT g FROM Goal g WHERE g.userId = :userId")
	List<Goal> findAllGoalsByUserId(String userId);
	
	@Query("SELECT g FROM Goal g WHERE g.userId = :userId " +
			"AND g.currentAmount < g.targetAmount " +
			"AND g.targetDate > CURRENT_DATE")
	List<Goal> findActiveGoalsByUserId(String userId);
	
	@Query("SELECT g FROM Goal g WHERE g.userId = :userId " +
			"AND (g.currentAmount = g.targetAmount " +
			"OR g.targetDate < CURRENT_DATE)")
	List<Goal> findInactiveGoalsByUserId(String userId);

}
