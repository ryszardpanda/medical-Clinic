package com.ryszardpanda.medicalClinic.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PersonIdAlreadyExist extends RuntimeException{

    private final HttpStatus httpStatus;

    public PersonIdAlreadyExist(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
