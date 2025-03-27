package com.simplify.exception;

public class RecommendationException extends RuntimeException {
    public RecommendationException() {
        super();
    }

    public RecommendationException(String message) {
        super(message);
    }
}