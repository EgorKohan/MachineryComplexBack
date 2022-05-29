package com.bsuir.advices;

import com.bsuir.exceptions.CustomValidationException;
import com.bsuir.validation.ValidationErrorResponse;
import com.bsuir.validation.Violation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse onConstraintValidationException(
            ConstraintViolationException e,
            HttpServletRequest request
    ) {
        List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(violation.getPropertyPath().toString(), e.getMessage()))
                .collect(Collectors.toList());
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(violations);
        return new ErrorResponse(
                validationErrorResponse,
                HttpStatus.BAD_REQUEST,
                request.getServletPath()
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(violations);
        return new ErrorResponse(
                validationErrorResponse,
                HttpStatus.BAD_REQUEST,
                request.getServletPath()
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    public ErrorResponse onCustomValidationException(
            CustomValidationException e,
            HttpServletRequest request
    ) {
        List<Violation> violations = e.getViolations();
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(violations);
        return new ErrorResponse(
                validationErrorResponse,
                HttpStatus.BAD_REQUEST,
                request.getServletPath()
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse onBindException(
            BindException e,
            HttpServletRequest request
    ) {
        List<Violation> violations = e.getBindingResult().getAllErrors().stream()
                .map(error -> new Violation(error.getCode(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(violations);
        return new ErrorResponse(
                validationErrorResponse,
                HttpStatus.BAD_REQUEST,
                request.getServletPath()
        );
    }

    @Data
    private static class ErrorResponse {
        private final Date timestamp = new Date();
        private final int status;
        private final String error;
        private final JsonNode message;
        private final String path;

        @SneakyThrows
        ErrorResponse(Object object, HttpStatus httpStatus, String path) {
            this.status = httpStatus.value();
            this.error = httpStatus.getReasonPhrase();
            this.path = path;
            this.message = new ObjectMapper().valueToTree(object);
        }

    }

}
