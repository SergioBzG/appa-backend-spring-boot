package com.sbz.appa.application.validator.annotation.impl;

import com.sbz.appa.application.validator.annotation.ValidNation;
import com.sbz.appa.commons.Nation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class NationValidator implements ConstraintValidator<ValidNation, String> {

    @Override
    public boolean isValid(String nation, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(Nation.values())
                .anyMatch(n -> n.name().equals(nation));
    }
}
