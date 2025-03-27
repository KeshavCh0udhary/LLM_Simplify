package com.simplify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when there are issues during the insurance purchase process.
 * Automatically returns a 400 Bad Request status when thrown from a controller.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PurchaseException extends RuntimeException {
    private final String errorCode;
    private final String details;
    private final String purchaseId;

    // Constructor with just a message
    public PurchaseException(String message) {
        super(message);
        this.errorCode = "PURCHASE_ERROR";
        this.details = null;
        this.purchaseId = null;
    }

    // Constructor with message and error code
    public PurchaseException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.details = null;
        this.purchaseId = null;
    }

    // Constructor with message and purchase ID
    public PurchaseException(String message, String errorCode, String purchaseId) {
        super(message);
        this.errorCode = errorCode;
        this.details = null;
        this.purchaseId = purchaseId;
    }

    // Full constructor with all fields
    public PurchaseException(String message, String errorCode, String details, String purchaseId) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
        this.purchaseId = purchaseId;
    }

    // Constructor with cause
    public PurchaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "PURCHASE_ERROR";
        this.details = cause != null ? cause.getMessage() : null;
        this.purchaseId = null;
    }

    // Getters
    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    @Override
    public String toString() {
        return "PurchaseException{" +
                "message='" + getMessage() + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", details='" + details + '\'' +
                ", purchaseId='" + purchaseId + '\'' +
                '}';
    }
}