package com.demo.LibraryManagement.Exception;

public class CopiesNotAvailableOrExceedException extends RuntimeException{
    public CopiesNotAvailableOrExceedException(String message) {
        super(message);
    }
}
