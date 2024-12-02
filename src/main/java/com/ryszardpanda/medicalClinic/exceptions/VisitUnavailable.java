package com.ryszardpanda.medicalClinic.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VisitUnavailable extends RuntimeException{
    private final HttpStatus httpStatus;

    public VisitUnavailable(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
