package com.skillstorm.project.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.skillstorm.project.dtos.UserDto;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@NotBlank
	@Column
	private String name;

	@NotBlank
	@Email
	@Column
	private String email;

	@NotBlank
	@Column
	private String password;
	
	public User() {
		super();
	}

	public User(
			long id, 
			@NotBlank String name, 
			@NotBlank @Email String email, 
			@NotBlank String password
	) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public User(UserDto userData) {
		super();
		this.id = userData.getId();
		this.name = userData.getName();
		this.email = userData.getEmail();
		this.password = userData.getPassword();
	}
	
	public UserDto toDto() {
		return new UserDto(
				this.id,
				this.name,
				this.email,
				this.password
				);
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

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
