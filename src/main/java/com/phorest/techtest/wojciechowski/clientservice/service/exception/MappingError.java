package com.phorest.techtest.wojciechowski.clientservice.service.exception;

public class MappingError extends RuntimeException {
    public MappingError(String message, Throwable exc) {
        super(message, exc);
    }
}
