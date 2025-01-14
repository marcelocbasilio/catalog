package com.marcelocbasilio.catalog.resources.exceptions;

import com.marcelocbasilio.catalog.services.exceptions.DatabaseException;
import com.marcelocbasilio.catalog.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return getTreatStandardError(exception, request, HttpStatus.NOT_FOUND, "Resource not found");
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException exception, HttpServletRequest request) {
        return getTreatStandardError(exception, request, HttpStatus.BAD_REQUEST, "Database Exception");
    }

    private ResponseEntity<StandardError> getTreatStandardError(Exception exception, HttpServletRequest request, HttpStatus status, String message) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError(message);
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
