package com.ryszardpanda.medicalClinic.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InstitutionNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public InstitutionNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
