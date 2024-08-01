package com.sbz.appa.application.validator.annotation.impl;

import com.sbz.appa.application.validator.annotation.ValidServiceType;
import com.sbz.appa.commons.ServiceType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ServiceTypeValidator implements ConstraintValidator<ValidServiceType, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(ServiceType.values())
                .anyMatch(serviceType -> serviceType.name().equals(type));
    }
}
