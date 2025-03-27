package com.simplify.controller;

import com.simplify.exception.InsuranceNotFoundException;
import com.simplify.exception.PolicyDocumentException;
import com.simplify.exception.PurchaseException;
import com.simplify.model.Insurance;
import com.simplify.model.PurchaseReceipt;
import com.simplify.model.UserProfile;
import com.simplify.services.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/insurances")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    // Create a new insurance policy
    @PostMapping()
    public ResponseEntity<Insurance> createInsurance(@RequestBody Insurance insurance) {
        Insurance savedInsurance = insuranceService.saveInsurance(insurance);
        return ResponseEntity.ok(savedInsurance);
    }
    
    // Get all insurance policies
    @GetMapping
    public ResponseEntity<List<Insurance>> getAllInsurances() {
        List<Insurance> insurances = insuranceService.getAllInsurances();
        return ResponseEntity.ok(insurances);
    }

    // Get insurances by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Insurance>> getInsurancesByType(@PathVariable String type) {
        List<Insurance> insurances = insuranceService.getInsurancesByType(type);
        return ResponseEntity.ok(insurances);
    }

    // Get recommended insurances based on user profile
    @GetMapping("/recommendations")
    public ResponseEntity<List<Insurance>> getRecommendedInsurances(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Double income,
            @RequestBody(required = false) UserProfile userProfile) {
        
        if (userProfile == null && age != null && gender != null && income != null) {
            userProfile = new UserProfile(age, gender, income);
        }
        
        List<Insurance> recommendations = insuranceService.getRecommendedInsurances(userProfile);
        return ResponseEntity.ok(recommendations);
    }

    // Get insurance by ID 
    @GetMapping("/{id}")
    public ResponseEntity<Insurance> getInsuranceById(@PathVariable Long id) {
        return insuranceService.getInsuranceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Purchase an insurance policy
    @PostMapping("/{id}/purchase")
    public ResponseEntity<PurchaseReceipt> purchaseInsurance(
            @PathVariable Long id, 
            @RequestBody UserProfile userProfile) {
        
        try {
            PurchaseReceipt receipt = insuranceService.purchaseInsurance(id, userProfile);
            return ResponseEntity.ok(receipt);
        } catch (InsuranceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PurchaseException("Failed to complete purchase: " + e.getMessage());
        }
    }

    // Download policy document (only available after purchase)
    @GetMapping("/{id}/policy-document")
    public ResponseEntity<byte[]> downloadPolicyDocument(@PathVariable Long id) {
        try {
            // Verify the insurance policy exists and is purchased
            insuranceService.verifyInsurancePurchase(id);
            
            // Load the PDF file from resources
            Resource resource = new ClassPathResource("templates/Krishna_Keshav_Resume.pdf");
            
            if (!resource.exists()) {
                throw new PolicyDocumentException("Policy document not available");
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                           "attachment; filename=\"policy_document_" + id + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(StreamUtils.copyToByteArray(resource.getInputStream()));
                    
        } catch (InsuranceNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw new PolicyDocumentException("Failed to process document: " + e.getMessage());
        }
    }
}