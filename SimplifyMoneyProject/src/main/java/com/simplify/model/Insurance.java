package com.simplify.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "Insurance")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UniqueElements
    private Long id;
    private String type;
    private String name;
    private String description;
    private Double premium;
    private String coverageDetails;
    private double popularityScore; 
    private String riskCategory;     
    private int minAge;              
    private int maxAge;              
    private double minIncome;        
    
    // For purchased policies
    private String policyNumber;
    private LocalDateTime purchaseDate;
    private String policyDocumentPath;
    private boolean purchased;
    private Long userId;             // Link to user who purchased

    // Constructors
    public Insurance() {
    	purchased = false;
    }

    public Insurance(String type, String name, String description, Double premium, 
                   String coverageDetails, double popularityScore, String riskCategory,
                   int minAge, int maxAge, double minIncome) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.premium = premium;
        this.coverageDetails = coverageDetails;
        this.popularityScore = popularityScore;
        this.riskCategory = riskCategory;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.minIncome = minIncome;
        purchased = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public String getCoverageDetails() {
        return coverageDetails;
    }

    public void setCoverageDetails(String coverageDetails) {
        this.coverageDetails = coverageDetails;
    }

    public double getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(double popularityScore) {
        this.popularityScore = popularityScore;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public double getMinIncome() {
        return minIncome;
    }

    public void setMinIncome(double minIncome) {
        this.minIncome = minIncome;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPolicyDocumentPath() {
        return policyDocumentPath;
    }

    public void setPolicyDocumentPath(String policyDocumentPath) {
        this.policyDocumentPath = policyDocumentPath;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Business methods
    public boolean isEligibleForUser(UserProfile user) {
        return user.getAge() >= minAge && 
               user.getAge() <= maxAge && 
               user.getIncome() >= minIncome;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", premium=" + premium +
                ", purchased=" + purchased +
                '}';
    }
}