package com.simplify.exception;

public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException() {
        super();
    }

    public PolicyNotFoundException(String message) {
        super(message);
    }
}