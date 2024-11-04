package com.ryszardpanda.medicalClinic.exceptions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MedicalClinicExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PatientNotFoundException.class})
    public ResponseEntity<Object> handlePatientNotFoundException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                new ErrorMessage("Nie znaleziono danego użytkownika"), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleDefaultException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                new ErrorMessage("Unknown Error"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}