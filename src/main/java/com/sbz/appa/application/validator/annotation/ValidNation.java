package com.sbz.appa.application.validator.annotation;

import com.sbz.appa.application.validator.annotation.impl.NationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NationValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNation {
    String message () default "invalid nation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
