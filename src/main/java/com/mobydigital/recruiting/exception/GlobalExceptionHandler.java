package com.mobydigital.recruiting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<HttpStatus> dataAlreadyExistException() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpStatus> candidateNotFoundHandler() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
