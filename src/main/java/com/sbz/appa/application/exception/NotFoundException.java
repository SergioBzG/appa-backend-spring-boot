package com.sbz.appa.application.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String resourceName) {
        super(resourceName + " not found");
    }
}
