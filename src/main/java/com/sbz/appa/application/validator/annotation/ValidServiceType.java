package com.sbz.appa.application.validator.annotation;

import com.sbz.appa.application.validator.annotation.impl.ServiceTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ServiceTypeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidServiceType {
    String message () default "invalid service type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
