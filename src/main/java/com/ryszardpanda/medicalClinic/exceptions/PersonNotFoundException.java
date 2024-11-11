package com.ryszardpanda.medicalClinic.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PersonNotFoundException extends RuntimeException{

    private final HttpStatus status;

    public PersonNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
