package com.project.springcloud.book.exception;

import com.project.springcloud.book.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> bookNotFound(BookNotFoundException ex, WebRequest webRequest){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                webRequest.getDescription(false),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorResponse> OutOfStock(OutOfStockException ex, WebRequest webRequest){
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
