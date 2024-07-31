package com.sbz.appa.application.exception;

public class InvalidOrMissingDataException extends RuntimeException {

    public InvalidOrMissingDataException(String resourceName, String attributeName) {
        super("invalid or missing " + attributeName + " in "  + resourceName);
    }
}
