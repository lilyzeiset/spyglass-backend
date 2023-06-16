package com.skillstorm.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.project.models.Goal;

public interface GoalRepository extends JpaRepository<Goal,	Long>{

}
