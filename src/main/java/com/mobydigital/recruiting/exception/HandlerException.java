package com.mobydigital.recruiting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<String> dataAlreadyExistException(Exception e) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> candidateNotFoundHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }


}
