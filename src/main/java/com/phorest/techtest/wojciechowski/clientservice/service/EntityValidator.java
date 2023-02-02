package com.phorest.techtest.wojciechowski.clientservice.service;

import com.phorest.techtest.wojciechowski.clientservice.service.exception.ValidationError;

public interface EntityValidator<T> {
    public void validate(T entity) throws ValidationError;

}
