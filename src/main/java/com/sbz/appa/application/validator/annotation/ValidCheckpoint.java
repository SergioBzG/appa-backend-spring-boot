package com.sbz.appa.application.validator.annotation;

import com.sbz.appa.application.validator.annotation.impl.CheckpointValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckpointValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCheckpoint {
    String message () default "invalid checkpoint";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
