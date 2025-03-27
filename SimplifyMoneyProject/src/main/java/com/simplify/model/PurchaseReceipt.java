package com.simplify.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PurchaseReceipt {
    private String transactionId;
    private Insurance insurance;
    private UserProfile user;
    private LocalDateTime purchaseDate;
    private double amountPaid;
    private String paymentMethod;
    private String status;
    private String policyNumber;
    private LocalDateTime coverageStartDate;
    private LocalDateTime coverageEndDate;
    
    // Default constructor
    public PurchaseReceipt() {
        this.transactionId = UUID.randomUUID().toString();
        this.purchaseDate = LocalDateTime.now();
        this.status = "COMPLETED";
    }
    
    // Constructor with required fields
    public PurchaseReceipt(Insurance insurance, UserProfile user, double amountPaid) {
        this();
        this.insurance = insurance;
        this.user = user;
        this.amountPaid = amountPaid;
        this.policyNumber = generatePolicyNumber();
        this.coverageStartDate = LocalDateTime.now();
        this.coverageEndDate = calculateCoverageEndDate();
    }
    
    // Helper method to generate policy number
    private String generatePolicyNumber() {
        return "POL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    // Helper method to calculate coverage end date (1 year from purchase)
    private LocalDateTime calculateCoverageEndDate() {
        return this.coverageStartDate.plusYears(1);
    }
    
    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public LocalDateTime getCoverageStartDate() {
        return coverageStartDate;
    }

    public void setCoverageStartDate(LocalDateTime coverageStartDate) {
        this.coverageStartDate = coverageStartDate;
    }

    public LocalDateTime getCoverageEndDate() {
        return coverageEndDate;
    }

    public void setCoverageEndDate(LocalDateTime coverageEndDate) {
        this.coverageEndDate = coverageEndDate;
    }

    @Override
    public String toString() {
        return "PurchaseReceipt{" +
                "transactionId='" + transactionId + '\'' +
                ", insurance=" + insurance.getName() +
                ", user=" + user.getName() +
                ", purchaseDate=" + purchaseDate +
                ", amountPaid=" + amountPaid +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", coverageStartDate=" + coverageStartDate +
                ", coverageEndDate=" + coverageEndDate +
                '}';
    }

	public void setTransactionId(String transactionId) {
		// TODO Auto-generated method stub
		this.transactionId = transactionId;
	}
}