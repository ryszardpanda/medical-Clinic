package com.ryszardpanda.medicalClinic.exceptions;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends RuntimeException{

    private final HttpStatus status;

    public PatientNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
