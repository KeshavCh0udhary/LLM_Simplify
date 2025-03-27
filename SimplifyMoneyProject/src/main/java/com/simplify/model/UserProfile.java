package com.simplify.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    
	@Column(nullable = false)
	private String name;
    
    private int age;
    private String gender;
    private double income;
    private boolean married;
    private boolean hasChildren;
    private boolean homeOwner;
    private boolean hasVehicle;
    private int travelFrequency;
    private String occupation; 
    private String healthStatus;
    
    // Constructors
    public UserProfile() {
        // Default constructor
    }
    
    // Constructor for basic profile
    public UserProfile(int age, String gender, double income) {
        this.age = age;
        this.gender = gender;
        this.income = income;
    }
    
    // Full constructor
    public UserProfile(String name, int age, String gender, double income, boolean married, 
                      boolean hasChildren, boolean homeOwner, boolean hasVehicle,
                      int travelFrequency, String occupation, String healthStatus) {
    	this.name = name;
        this.age = age;
        this.gender = gender;
        this.income = income;
        this.married = married;
        this.hasChildren = hasChildren;
        this.homeOwner = homeOwner;
        this.hasVehicle = hasVehicle;
        this.travelFrequency = travelFrequency;
        this.occupation = occupation;
        this.healthStatus = healthStatus;
    }
    
    // Getters and setters
    public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
    
    public int getName() { 
        return age; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    public int getAge() { 
        return age; 
    }
    
    public void setAge(int age) { 
        this.age = age; 
    }
    
    public String getGender() { 
        return gender; 
    }
    
    public void setGender(String gender) { 
        this.gender = gender; 
    }
    
    public double getIncome() { 
        return income; 
    }
    
    public void setIncome(double income) { 
        this.income = income; 
    }
    
    public boolean isMarried() { 
        return married; 
    }
    
    public void setMarried(boolean married) { 
        this.married = married; 
    }
    
    public boolean hasChildren() { 
        return hasChildren; 
    }
    
    public void setHasChildren(boolean hasChildren) { 
        this.hasChildren = hasChildren; 
    }
    
    public boolean isHomeOwner() { 
        return homeOwner; 
    }
    
    public void setHomeOwner(boolean homeOwner) { 
        this.homeOwner = homeOwner; 
    }
    
    public boolean hasVehicle() { 
        return hasVehicle; 
    }
    
    public void setHasVehicle(boolean hasVehicle) { 
        this.hasVehicle = hasVehicle; 
    }
    
    public int getTravelFrequency() {
        return travelFrequency;
    }
    
    public void setTravelFrequency(int travelFrequency) {
        this.travelFrequency = travelFrequency;
    }
    
    public String getOccupation() {
        return occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String getHealthStatus() {
        return healthStatus;
    }
    
    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }
    
    // Helper methods
    public boolean isHighIncome() {
        return income > 1000000;
    }
    
    public boolean isYoungAdult() {
        return age >= 18 && age <= 25;
    }
    
    public boolean isSenior() {
        return age >= 60;
    }
    
    public boolean needsFamilyCoverage() {
        return married || hasChildren;
    }
    
    @Override
    public String toString() {
        return "UserProfile{" +
                "age=" + age +
                ", gender='" + gender + '\'' +
                ", income=" + income +
                ", married=" + married +
                ", hasChildren=" + hasChildren +
                ", homeOwner=" + homeOwner +
                ", hasVehicle=" + hasVehicle +
                ", travelFrequency=" + travelFrequency +
                ", occupation='" + occupation + '\'' +
                ", healthStatus='" + healthStatus + '\'' +
                '}';
    }
}