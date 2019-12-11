package com.easytestit.generatexml;

public class ValidationException extends RuntimeException {

    public ValidationException(Exception e) {
        super(e);
    }

    ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Exception exception) {
        super(message, exception);
    }
}
