package com.example.lab5.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    protected CustomException(String message) {
        super(message);
    }

    protected CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getHttpStatus();
}
