package com.sbz.appa.application.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String resourceName, String attributeName) {
        super( resourceName + " with this " + attributeName + " already exists");
    }
}
