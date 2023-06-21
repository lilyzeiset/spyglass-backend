package com.skillstorm.project.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.project.dtos.UserDto;
import com.skillstorm.project.models.AppUser;
import com.skillstorm.project.repositories.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public List<UserDto> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(AppUser::toDto)
				.collect(Collectors.toList());
	}

	public UserDto getUserById(long id) {
		AppUser user = userRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException());
		return user.toDto();
	}

	public UserDto createUser(@Valid UserDto userData) {
		AppUser user = new AppUser(userData);
		return userRepository.save(user).toDto();
	}

	public UserDto updateUser(long id, @Valid UserDto userData) {
		AppUser user = new AppUser(userData);
		user.setId(id);
		return userRepository.save(user).toDto();
	}

	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}

}
