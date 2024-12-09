package com.ryszardpanda.medicalClinic.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DoctorNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public DoctorNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
