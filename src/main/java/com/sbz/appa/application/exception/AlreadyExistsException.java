package com.sbz.appa.application.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String resourceName) {
        super("this " + resourceName + " already exists");
    }
}
