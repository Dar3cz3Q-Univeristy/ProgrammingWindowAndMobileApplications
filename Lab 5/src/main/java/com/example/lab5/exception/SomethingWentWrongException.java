package com.example.lab5.exception;

import org.springframework.http.HttpStatus;

public class SomethingWentWrongException extends CustomException {
    private static final String MESSAGE = "Something went wrong! Try again.";

    public SomethingWentWrongException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
