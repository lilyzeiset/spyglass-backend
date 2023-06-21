package com.skillstorm.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.skillstorm.project.models.Goal;

public interface GoalRepository extends JpaRepository<Goal,	Long>{

	@Query("SELECT g FROM Goal g WHERE g.userId = :userId")
	List<Goal> findAllGoalsByUserId(String userId);

}
