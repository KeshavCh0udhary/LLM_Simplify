package com.simplify.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String errorCode;
    private String path;
    private Map<String, String> validationErrors;
    private List<String> suggestions;
    private String referenceId;

    // Constructors
    public ErrorDetails() {
        this.timestamp = LocalDateTime.now();
    }

    // Basic constructor
    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this();
        this.message = message;
        this.details = details;
    }

    // Full constructor
    public ErrorDetails(LocalDateTime timestamp, String message, String details, 
                      String errorCode, String path, Map<String, String> validationErrors,
                      List<String> suggestions, String referenceId) {
        this();
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
        this.path = path;
        this.validationErrors = validationErrors;
        this.suggestions = suggestions;
        this.referenceId = referenceId;
    }

    // Builder-style static method
    public static ErrorDetailsBuilder builder() {
        return new ErrorDetailsBuilder();
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    // Builder class
    public static class ErrorDetailsBuilder {
        private LocalDateTime timestamp;
        private String message;
        private String details;
        private String errorCode;
        private String path;
        private Map<String, String> validationErrors;
        private List<String> suggestions;
        private String referenceId;

        public ErrorDetailsBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorDetailsBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorDetailsBuilder details(String details) {
            this.details = details;
            return this;
        }

        public ErrorDetailsBuilder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorDetailsBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ErrorDetailsBuilder validationErrors(Map<String, String> validationErrors) {
            this.validationErrors = validationErrors;
            return this;
        }

        public ErrorDetailsBuilder suggestions(List<String> suggestions) {
            this.suggestions = suggestions;
            return this;
        }

        public ErrorDetailsBuilder referenceId(String referenceId) {
            this.referenceId = referenceId;
            return this;
        }

        public ErrorDetails build() {
            return new ErrorDetails(
                timestamp != null ? timestamp : LocalDateTime.now(),
                message,
                details,
                errorCode,
                path,
                validationErrors,
                suggestions,
                referenceId
            );
        }
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", path='" + path + '\'' +
                ", validationErrors=" + validationErrors +
                ", suggestions=" + suggestions +
                ", referenceId='" + referenceId + '\'' +
                '}';
    }
}