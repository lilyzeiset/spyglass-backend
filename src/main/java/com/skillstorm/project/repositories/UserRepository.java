package com.skillstorm.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.project.models.User;

public interface UserRepository extends JpaRepository<User,	Long>{

}
