package com.simform.studentslf4j.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> exceptionMethod(NotFoundException notFoundException){
    return new ResponseEntity<>(notFoundException.getMessage() , HttpStatus.NOT_FOUND);
  }

}
