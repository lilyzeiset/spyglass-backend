package com.skillstorm.project.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.skillstorm.project.dtos.GoalDto;

@Entity
public class Goal {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
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

	public Goal(long id, 
				String name, 
				String description, 
				String imagePath, 
				LocalDate targetDate,
				double targetAmount, 
				double currentAmount
	) {
		super();
		this.id = id;
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
