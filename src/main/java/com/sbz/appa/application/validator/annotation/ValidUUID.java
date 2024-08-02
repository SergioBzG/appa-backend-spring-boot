package com.sbz.appa.application.validator.annotation;


import com.sbz.appa.application.validator.annotation.impl.UUIDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UUIDValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {
    String message () default "invalid uuid format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
