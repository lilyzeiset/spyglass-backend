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

import com.skillstorm.project.dtos.UserDto;
import com.skillstorm.project.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserControllerOld {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<UserDto> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public UserDto getUserById(@PathVariable long id) {
		return userService.getUserById(id);
	}
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userData) {
		UserDto createdUser = userService.createUser(userData);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public UserDto updateUser(@PathVariable long id, @Valid @RequestBody UserDto userData) {
		return userService.updateUser(id, userData);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
	}
}
