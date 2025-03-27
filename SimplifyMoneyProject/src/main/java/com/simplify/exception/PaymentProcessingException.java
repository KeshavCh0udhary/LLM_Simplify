package com.simplify.exception;

public class PaymentProcessingException extends RuntimeException {
    public PaymentProcessingException() {
        super();
    }

    public PaymentProcessingException(String message) {
        super(message);
    }
}