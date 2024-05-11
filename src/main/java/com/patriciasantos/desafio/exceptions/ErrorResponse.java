package com.patriciasantos.desafio.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorResponse {

    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    public ErrorResponse(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    private static class ValidationError {
        private final String field;
        private final String message;

        public ValidationError(final String field, final String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return this.field;
        }

        public String getMessage() {
            return this.message;
        }
    }


    public int getStatus() {
        return this.status;
    }


    public String getMessage() {
        return this.message;
    }


    public String getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(final String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public List<ValidationError> getErrors() {
        return this.errors;
    }

    public void setErrors(final List<ValidationError> errors) {
        this.errors = errors;
    }
    

    
    public void addValidationError(final String field, final String message) {
        if (Objects.isNull(errors)) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(new ValidationError(field, message));
    }

    public String toJson() {
        return "{\"status\": " + getStatus() + ", " +
                "\"message\": \"" + getMessage() + "\"}";
    }

}