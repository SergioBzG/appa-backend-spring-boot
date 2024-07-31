package com.sbz.appa.application.exception;

public class ActionNotAllowedException extends RuntimeException{

    public ActionNotAllowedException(String resourceName, String action) {
        super(action + " this " + resourceName + " is not allowed");
    }
}
