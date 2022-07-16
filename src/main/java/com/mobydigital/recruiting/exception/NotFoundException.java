package com.mobydigital.recruiting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class NotFoundException extends Exception {
    public NotFoundException(String msg) {
        super(msg);
    }
}
