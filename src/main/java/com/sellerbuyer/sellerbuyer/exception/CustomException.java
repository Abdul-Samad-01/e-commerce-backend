package com.sellerbuyer.sellerbuyer.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class that can hold an error
 *  message and the corresponding HTTP status.
 */
@Data
public class CustomException extends RuntimeException {
    private String errorMessage;
    private HttpStatus httpStatus;

    public CustomException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public CustomException(HttpStatus httpStatus, String errorMessage) {
        this(errorMessage);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
