package com.demo.LibraryManagement.Exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
       super(message);
    }
}
