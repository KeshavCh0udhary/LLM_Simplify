package com.simplify.exception;

public class InvalidInsuranceDataException extends RuntimeException {
    public InvalidInsuranceDataException() {
        super();
    }

    public InvalidInsuranceDataException(String message) {
        super(message);
    }
}