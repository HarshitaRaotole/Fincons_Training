package com.example.parking_management.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice // Handles exceptions globally for all controllers
public class GlobalExceptionHandler {

    // Handles custom parking-related exceptions
    @ExceptionHandler(ParkingException.class)
    public ResponseEntity<String> handleParkingException(ParkingException e){
        // Conflict cases like slot full or vehicle already parked
        if(e.getMessage().contains("Full") || e.getMessage().contains("already")){
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.CONFLICT);
        }
        // Resource not found cases
        if(e.getMessage().contains("not found")){
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.NOT_FOUND);
        }
        // Default bad request response
        return new ResponseEntity<>(e.getMessage(),
        HttpStatus.BAD_REQUEST);
    }

    // Handles validation errors from request body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(
            MethodArgumentNotValidException e){
        // Fetching first validation error message
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }
    // Handles database constraint violations (like duplicate entries)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(
            DataIntegrityViolationException e){
        return new ResponseEntity<>(
                "Duplicate entries not allowed",
                HttpStatus.CONFLICT
        );
    }
}




