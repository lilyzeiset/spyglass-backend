package com.skillstorm.project.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;

import com.skillstorm.project.dtos.GoalDto;
import com.skillstorm.project.services.UserService;

@Entity
public class Goal {
	

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;

//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private AppUser user;
	
	@Column
	private String userId;
	
	@NotBlank
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private String imagePath;
	
	@Column
	private LocalDate targetDate;
	
	@Positive
	@Column
	private double targetAmount;
	
	@Column
	private double currentAmount;
	

	public Goal() {
		super();
	}
	
	public Goal(GoalDto goalData) {
		this.id = goalData.getId();
		this.userId = goalData.getUserId();
		this.name = goalData.getName();
		this.description = goalData.getDescription();
		this.imagePath = goalData.getImagePath();
		this.targetDate = goalData.getTargetDate();
		this.targetAmount = goalData.getTargetAmount();
		this.currentAmount = goalData.getCurrentAmount();
	}

	public Goal(
			long id, 
			String userId,
			String name, 
			String description, 
			String imagePath, 
			LocalDate targetDate,
			double targetAmount, 
			double currentAmount
	) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.imagePath = imagePath;
		this.targetDate = targetDate;
		this.targetAmount = targetAmount;
		this.currentAmount = currentAmount;
	}
	
	public GoalDto toDto() {
		return new GoalDto(
				this.id,
				this.userId,
				this.name,
				this.description,
				this.imagePath,
				this.targetDate,
				this.targetAmount,
				this.currentAmount
				);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public AppUser getUser() {
//		return user;
//	}
//
//	public void setUser(AppUser user) {
//		this.user = user;
//	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}

	public double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(double targetAmount) {
		this.targetAmount = targetAmount;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount) {
		this.currentAmount = currentAmount;
	}
	
	
}
