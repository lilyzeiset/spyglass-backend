package com.skillstorm.project.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

	private long id;
	
	@NotBlank 
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	public UserDto() {
		super();
	}

	public UserDto(long id, @NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
