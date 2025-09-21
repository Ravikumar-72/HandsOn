package com.project.springcloud.customer.exception;

import com.project.springcloud.customer.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFound(CustomerNotFoundException ex, WebRequest webRequest){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                webRequest.getDescription(false),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ExistingCustomerFoundException.class)
    public ResponseEntity<ErrorResponse> existingUserFound(ExistingCustomerFoundException ex, WebRequest webRequest){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                webRequest.getDescription(false),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> GlobalException(Exception ex, WebRequest webRequest){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                webRequest.getDescription(false),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
