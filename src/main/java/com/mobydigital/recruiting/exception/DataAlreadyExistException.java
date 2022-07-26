package com.mobydigital.recruiting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class DataAlreadyExistException extends RuntimeException {
    public DataAlreadyExistException(String message) {
        super(message);
    }
}
