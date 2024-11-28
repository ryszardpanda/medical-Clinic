package com.ryszardpanda.medicalClinic.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserDataException extends RuntimeException {

    private HttpStatus httpStatus;

    public InvalidUserDataException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
