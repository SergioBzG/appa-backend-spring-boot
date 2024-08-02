package com.sbz.appa.application.validator.annotation.impl;

import com.sbz.appa.application.validator.annotation.ValidUUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<ValidUUID, String> {

    private final static String UUID_PATTER = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext constraintValidatorContext) {
        return uuid.matches(UUID_PATTER);
    }
}
