package com.simplify.exception;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException() {
        super();
    }

    public UserProfileNotFoundException(String message) {
        super(message);
    }
}