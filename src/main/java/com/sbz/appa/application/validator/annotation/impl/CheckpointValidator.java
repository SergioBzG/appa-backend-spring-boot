package com.sbz.appa.application.validator.annotation.impl;

import com.sbz.appa.application.validator.annotation.ValidCheckpoint;
import com.sbz.appa.commons.Checkpoint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CheckpointValidator implements ConstraintValidator<ValidCheckpoint, String> {

    @Override
    public boolean isValid(String checkpoint, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(Checkpoint.values())
                .anyMatch(c -> c.name().equals(checkpoint));
    }
}
