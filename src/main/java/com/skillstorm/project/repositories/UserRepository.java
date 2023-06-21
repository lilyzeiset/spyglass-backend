package com.skillstorm.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorm.project.models.AppUser;

public interface UserRepository extends JpaRepository<AppUser,	Long>{

}
