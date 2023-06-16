package com.skillstorm.project.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.skillstorm.project.dtos.UserDto;
import com.skillstorm.project.models.User;
import com.skillstorm.project.repositories.UserRepository;

public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public List<UserDto> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(User::toDto)
				.collect(Collectors.toList());
	}

	public UserDto getUserById(long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException());
		return user.toDto();
	}

	public UserDto createUser(@Valid UserDto userData) {
		User user = new User(userData);
		return userRepository.save(user).toDto();
	}

	public UserDto updateUser(long id, @Valid UserDto userData) {
		User user = new User(userData);
		user.setId(id);
		return userRepository.save(user).toDto();
	}

	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}

}
