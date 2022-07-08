package com.mobydigital.recruiting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataAlreadyExistException extends Exception {
    public DataAlreadyExistException(String message) {
        super(message);
    }
}