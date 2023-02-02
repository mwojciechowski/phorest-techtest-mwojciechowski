package com.phorest.techtest.wojciechowski.clientservice.service.exception;

public class ValidationError extends Exception {
    public ValidationError(String message) {
        super(message);
    }

    public ValidationError(String message, Throwable exc) {
        super(message, exc);
    }
}
