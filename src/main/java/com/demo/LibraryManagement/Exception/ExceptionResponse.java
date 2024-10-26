package com.demo.LibraryManagement.Exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@Builder
public class ExceptionResponse {
 private HttpStatus status;
 private String message;

}
