package com.ryszardpanda.medicalClinic.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PatientIdAlreadyExist extends RuntimeException{

    private final HttpStatus httpStatus;

    public PatientIdAlreadyExist(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
