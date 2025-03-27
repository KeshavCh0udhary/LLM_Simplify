package com.simplify;

import com.simplify.controller.InsuranceController;
import com.simplify.model.Insurance;
import com.simplify.model.PurchaseReceipt;
import com.simplify.model.UserProfile;
import com.simplify.services.InsuranceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SimplifyTests {

    @Mock
    private InsuranceService insuranceService;

    @InjectMocks
    private InsuranceController insuranceController;

    private Insurance testInsurance;
    private UserProfile testUserProfile;

    @BeforeEach
    void setUp() {
        testInsurance = new Insurance();
        testInsurance.setId(1L);
        testInsurance.setName("Test Insurance");
        testInsurance.setType("HEALTH");
        testInsurance.setPremium(100.0);

        testUserProfile = new UserProfile();
        testUserProfile.setAge(30);
        testUserProfile.setGender("MALE");
        testUserProfile.setIncome(50000.0);
    }

    @Test
    void contextLoads() {
        assertNotNull(insuranceController);
    }

    @Test
    void testCreateInsurance() {
        when(insuranceService.saveInsurance(any(Insurance.class))).thenReturn(testInsurance);
        
        ResponseEntity<Insurance> response = insuranceController.createInsurance(testInsurance);
        
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Insurance", response.getBody().getName());
    }

    @Test
    void testGetAllInsurances() {
        when(insuranceService.getAllInsurances()).thenReturn(Arrays.asList(testInsurance));
        
        ResponseEntity<List<Insurance>> response = insuranceController.getAllInsurances();
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetInsurancesByType() {
        when(insuranceService.getInsurancesByType("HEALTH")).thenReturn(Arrays.asList(testInsurance));
        
        ResponseEntity<List<Insurance>> response = insuranceController.getInsurancesByType("HEALTH");
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("HEALTH", response.getBody().get(0).getType());
    }

    @Test
    void testGetRecommendedInsurances() {
        when(insuranceService.getRecommendedInsurances(testUserProfile))
            .thenReturn(Arrays.asList(testInsurance));
        
        ResponseEntity<List<Insurance>> response = 
            insuranceController.getRecommendedInsurances(null, "MALE", 50000.0, testUserProfile);
        
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetInsuranceById() {
        when(insuranceService.getInsuranceById(1L)).thenReturn(java.util.Optional.of(testInsurance));
        
        ResponseEntity<Insurance> response = insuranceController.getInsuranceById(1L);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testPurchaseInsurance() {
        // Create a mock PurchaseReceipt
        PurchaseReceipt mockReceipt = new PurchaseReceipt();
        mockReceipt.setTransactionId("TXN12345");
        mockReceipt.setInsurance(testInsurance);
        mockReceipt.setUser(testUserProfile);
        mockReceipt.setAmountPaid(testInsurance.getPremium());
        
        // Mock the service to return the receipt
        when(insuranceService.purchaseInsurance(1L, testUserProfile))
            .thenReturn(mockReceipt);
        
        // Call the controller method (note this should expect PurchaseReceipt, not String)
        ResponseEntity<PurchaseReceipt> response = insuranceController.purchaseInsurance(1L, testUserProfile);
        
        // Verify the response
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("TXN12345", response.getBody().getTransactionId());
        assertEquals(testInsurance.getPremium(), response.getBody().getAmountPaid());
    }
}