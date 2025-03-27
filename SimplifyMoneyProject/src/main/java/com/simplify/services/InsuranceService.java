package com.simplify.services;

import com.simplify.exception.InsuranceNotFoundException;
import com.simplify.exception.InsurancePurchaseException;
import com.simplify.exception.InvalidInsuranceDataException;
import com.simplify.exception.PolicyDocumentException;
import com.simplify.exception.PolicyNotFoundException;
import com.simplify.exception.RecommendationException;


import com.simplify.model.Insurance;
import com.simplify.model.PurchaseReceipt;
import com.simplify.model.UserProfile;
import com.simplify.repo.InsuranceRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsuranceService {
    @Autowired
    private InsuranceRepo insuranceRepository;

    @Value("${policy.docs.path}")
    private String policyDocsPath;

    @Value("${dummy.policy.file}")
    private String dummyPolicyFile;

    // Save an insurance policy
    public Insurance saveInsurance(Insurance insurance) {
        try {
            validateInsuranceData(insurance);
            return insuranceRepository.save(insurance);
        } catch (InvalidInsuranceDataException e) {
            throw e;
        } catch (Exception e) {
            throw new InsurancePurchaseException("Failed to save insurance policy: " + e.getMessage());
        }
    }
    
    public void addInsuranceList(List<Insurance> insurances) {
        insuranceRepository.saveAll(insurances);
    }

    // Get all insurance policies
    public List<Insurance> getAllInsurances() {
        try {
            return insuranceRepository.findAll();
        } catch (Exception e) {
            throw new RecommendationException("Failed to retrieve insurance policies: " + e.getMessage());
        }
    }

    // Find by type
    public List<Insurance> getInsurancesByType(String type) {
        try {
            if (type == null || type.trim().isEmpty()) {
                throw new InvalidInsuranceDataException("Insurance type cannot be empty");
            }
            return insuranceRepository.findByType(type);
        } catch (InvalidInsuranceDataException e) {
            throw e;
        } catch (Exception e) {
            throw new RecommendationException("Failed to retrieve insurance policies by type: " + e.getMessage());
        }
    }

    // Get recommended insurances
    public List<Insurance> getRecommendedInsurances(UserProfile user) {
        try {
            if (user == null) {
                return insuranceRepository.findTop5ByOrderByPopularityScoreDesc();
            }
            
            validateUserProfile(user);
            List<Insurance> allInsurances = insuranceRepository.findAll();
            
            return allInsurances.stream()
                    .filter(insurance -> isRelevantForUser(insurance, user))
                    .sorted((i1, i2) -> {
                        double score1 = calculateRelevanceScore(i1, user);
                        double score2 = calculateRelevanceScore(i2, user);
                        return Double.compare(score2, score1);
                    })
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (InvalidInsuranceDataException e) {
            throw new RecommendationException("Invalid user profile for recommendations: " + e.getMessage());
        } catch (Exception e) {
            throw new RecommendationException("Failed to generate insurance recommendations: " + e.getMessage());
        }
    }

    // Purchase insurance
    public PurchaseReceipt purchaseInsurance(Long id, UserProfile user) {
        try {
            Insurance insurance = insuranceRepository.findById(id)
                    .orElseThrow(() -> new InsuranceNotFoundException("Insurance policy not found with id: " + id));
            
            if (insurance.isPurchased()) {
                throw new InsurancePurchaseException("Insurance policy already purchased: " + id);
            }
            
            if (!isEligibleForUser(insurance, user)) {
                throw new InsurancePurchaseException("User is not eligible for this insurance policy");
            }
            
            String policyNumber = "POL-" + System.currentTimeMillis();
            String documentPath = generatePolicyDocument(insurance, policyNumber);
            
            insurance.setPurchased(true);
            insurance.setPolicyNumber(policyNumber);
            insurance.setPurchaseDate(LocalDateTime.now());
            insurance.setPolicyDocumentPath(documentPath);
            insurance.setUserId(user.getId());
            
            Insurance i = insuranceRepository.save(insurance);
            double max = 5000000.00;
            double min = 5000.00;
            double range = max - min + 1;

            // generate random numbers within 1 to 10
            double random = Math.random() * range + min;
              
            return new PurchaseReceipt(i,user,random);
        } catch (InsuranceNotFoundException | InsurancePurchaseException e) {
            throw e;
        } catch (Exception e) {
            throw new InsurancePurchaseException("Failed to purchase insurance policy: " + e.getMessage());
        }
    }

    // Download policy document
    public byte[] downloadPolicyDocument(Long insuranceId) {
        try {
            Insurance insurance = insuranceRepository.findById(insuranceId)
                    .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + insuranceId));
            
            if (insurance.getPolicyDocumentPath() == null) {
                throw new PolicyDocumentException("Policy document not generated yet");
            }
            
            Resource resource = new ClassPathResource(dummyPolicyFile);
            return resource.getContentAsByteArray();
        } catch (PolicyNotFoundException | PolicyDocumentException e) {
            throw e;
        } catch (IOException e) {
            throw new PolicyDocumentException("Failed to read policy document: " + e.getMessage());
        } catch (Exception e) {
            throw new PolicyDocumentException("Failed to download policy document: " + e.getMessage());
        }
    }

    // Get insurance by ID
    public Optional<Insurance> getInsuranceById(Long id) {
        try {
            return insuranceRepository.findById(id);
        } catch (Exception e) {
            throw new RecommendationException("Failed to retrieve insurance by id: " + e.getMessage());
        }
    }

    // Validation methods
    private void validateInsuranceData(Insurance insurance) {
        if (insurance == null) {
            throw new InvalidInsuranceDataException("Insurance data cannot be null");
        }
        if (insurance.getType() == null || insurance.getType().trim().isEmpty()) {
            throw new InvalidInsuranceDataException("Insurance type is required");
        }
        if (insurance.getName() == null || insurance.getName().trim().isEmpty()) {
            throw new InvalidInsuranceDataException("Insurance name is required");
        }
        if (insurance.getPremium() == null || insurance.getPremium() <= 0) {
            throw new InvalidInsuranceDataException("Premium amount must be positive");
        }
    }

    private void validateUserProfile(UserProfile user) {
        if (user.getAge() <= 0) {
            throw new InvalidInsuranceDataException("Invalid age value");
        }
        if (user.getIncome() < 0) {
            throw new InvalidInsuranceDataException("Income cannot be negative");
        }
    }

    private boolean isEligibleForUser(Insurance insurance, UserProfile user) {
        return user.getAge() >= insurance.getMinAge() && 
               user.getAge() <= insurance.getMaxAge() && 
               user.getIncome() >= insurance.getMinIncome();
    }

    // Helper methods (same as before)
    private boolean isRelevantForUser(Insurance insurance, UserProfile user) {
        String type = insurance.getType().toLowerCase();
        
        if (user.getAge() < 18) {
            return type.contains("child") || type.contains("education");
        }
        
        if (user.getAge() > 60) {
            return type.contains("senior") || type.contains("health") || type.contains("life");
        }
        
        if (user.getIncome() > 500000) {
            return !type.contains("basic");
        }
        
        switch (type) {
            case "health":
                return true;
            case "auto":
                return user.hasVehicle();
            case "home":
                return user.isHomeOwner();
            case "life":
                return user.getAge() >= 18 && user.getAge() <= 65;
            case "travel":
                return user.getTravelFrequency() > 0;
            default:
                return true;
        }
    }

    private double calculateRelevanceScore(Insurance insurance, UserProfile user) {
        double score = 0;
        String type = insurance.getType().toLowerCase();
        
        if (user.getAge() < 25 && type.contains("education")) {
            score += 2;
        }
        if (user.getAge() > 60 && (type.contains("senior") || type.contains("health"))) {
            score += 2;
        }
        
        if (user.getIncome() > 1000000 && type.contains("premium")) {
            score += 1.5;
        } else if (user.getIncome() < 300000 && type.contains("basic")) {
            score += 1.5;
        }
        
        if (user.isMarried() && type.contains("family")) {
            score += 1;
        }
        if (user.hasChildren() && type.contains("child")) {
            score += 1.5;
        }
        
        score += insurance.getPopularityScore() * 0.5;
        
        return score;
    }

    private String generatePolicyDocument(Insurance insurance, String policyNumber) {
        String fileName = "policy_" + policyNumber + ".pdf";
        return policyDocsPath + "/" + fileName;
    }

	public void verifyInsurancePurchase(Long id) {
		
		return;
	}
}