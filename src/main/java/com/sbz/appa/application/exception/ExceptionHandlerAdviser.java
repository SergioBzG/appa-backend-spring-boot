package com.sbz.appa.application.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdviser {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errorDetails = new HashMap<>();
        for(FieldError fieldError : exception.getFieldErrors()){
            errorDetails.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                errorDetails.toString()
        );
        problemDetail.setTitle(String.format("%s contains invalid fields", exception.getObjectName()));
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail onConstraintViolationException(ConstraintViolationException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidOrMissingDataException.class)
    public ProblemDetail onInvalidOrMissingDataException(InvalidOrMissingDataException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        problemDetail.setTitle("invalid or missing data");
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail onException(Exception exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage()
        );
    }

}
