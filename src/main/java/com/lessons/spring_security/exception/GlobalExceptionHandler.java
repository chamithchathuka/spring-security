package com.lessons.spring_security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException ex) {
        // Custom error message
        String errorMessage = "A database constraint was violated: " + ex.getMessage();

        // Return a response entity with the error message and HTTP status code
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
