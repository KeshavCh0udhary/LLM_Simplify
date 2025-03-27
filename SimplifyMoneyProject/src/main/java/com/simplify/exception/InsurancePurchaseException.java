package com.simplify.exception;

public class InsurancePurchaseException extends RuntimeException {
    public InsurancePurchaseException() {
        super();
    }

    public InsurancePurchaseException(String message) {
        super(message);
    }
}