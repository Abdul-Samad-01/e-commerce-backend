package com.sellerbuyer.sellerbuyer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Global exception handler for the application, providing centralized
 * exception handling across all @RequestMapping methods.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleCustomException(CustomException ex) {
        HttpStatus status = (ex.getHttpStatus() == null) ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getHttpStatus();
        CustomExceptionResponse exceptionResponse = new CustomExceptionResponse(status, new Date(), ex.getErrorMessage());
        return ResponseEntity.status(status).body(exceptionResponse);
    }
}
