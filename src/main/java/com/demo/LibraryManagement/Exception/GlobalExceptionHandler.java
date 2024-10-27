package com.demo.LibraryManagement.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionResponse handleResourceNotFound(ResourceNotFoundException exception){
      return  ExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
    }
    @ExceptionHandler(CopiesNotAvailableOrExceedException.class)
    public ExceptionResponse handleCopiesUnavailable(CopiesNotAvailableOrExceedException exception){
        return ExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
    }
}
