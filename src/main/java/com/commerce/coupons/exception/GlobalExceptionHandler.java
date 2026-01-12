package com.commerce.coupons.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity.badRequest()
        .body(ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            ex.getMessage()
        ));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ProblemDetail> handleDuplicateKey(
      DataIntegrityViolationException ex
  ) {
    return ResponseEntity.badRequest()
        .body(ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT,
            "Coupon name or code already exists"
        ));
  }
}
