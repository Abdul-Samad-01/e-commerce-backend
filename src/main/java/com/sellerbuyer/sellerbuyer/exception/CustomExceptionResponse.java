package com.sellerbuyer.sellerbuyer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Represents the structured response for an exception in the seller-buyer System.
 * This class includes the HTTP status code, timestamp, and a message describing the error.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomExceptionResponse {
    private HttpStatus statusCode;
    private Date timestamp; // The timestamp indicating when the error occurred
    private String errorMessage;
}
