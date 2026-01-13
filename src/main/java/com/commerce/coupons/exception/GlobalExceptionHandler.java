package com.commerce.coupons.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
    log.warn("Bad Request: {}", ex.getMessage());
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST,
        ex.getMessage()
    );
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ProblemDetail handleDuplicateKey(
      DataIntegrityViolationException ex
  ) {
    log.warn("Conflict: {}", ex.getMessage());
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.CONFLICT,
        "Coupon name or code already exists"
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleValidationErrors(
      MethodArgumentNotValidException ex
  ) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problem.setTitle("Validation failed");

    Map<String, String> errors = new LinkedHashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

    problem.setProperty("errors", errors);
    log.warn("Validation errors: {}", errors);
    return problem;
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleEntityNotFound(
      EntityNotFoundException ex
  ) {
    log.warn("Entity Not Found: {}", ex.getMessage());
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.NOT_FOUND,
        ex.getMessage()
    );
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ProblemDetail handleAllExceptions(Exception ex) {
    log.error("Unhandled exception occurred", ex);

    return ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Something went wrong. Please contact support."
    );
  }
}
