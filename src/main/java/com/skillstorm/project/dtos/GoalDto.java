package com.skillstorm.project.dtos;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class GoalDto {

	private long id;
	private String userId;
	@NotBlank private String name;
	private String description;
	private String imagePath;
	private LocalDate targetDate;
	@Positive private double targetAmount;
	private double currentAmount;
	
	public GoalDto() {
		super();
	}

	public GoalDto(long id, 
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
