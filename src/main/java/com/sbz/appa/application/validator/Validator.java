package com.sbz.appa.application.validator;

public interface Validator<T> {

    boolean validate(T dto);
}
