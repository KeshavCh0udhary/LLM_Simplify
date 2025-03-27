package com.simplify.exception;

public class PolicyDocumentException extends RuntimeException {
    public PolicyDocumentException() {
        super();
    }

    public PolicyDocumentException(String message) {
        super(message);
    }
}