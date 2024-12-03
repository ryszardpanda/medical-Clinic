package com.ryszardpanda.medicalClinic.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class MedicalClinicExceptionHandler {
    @ExceptionHandler({PersonNotFoundException.class})
    public ResponseEntity<ErrorMessage> handlePatientNotFoundException(
            PersonNotFoundException ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getStatus());
    }

    @ExceptionHandler({NoIdNumberException.class})
    public ResponseEntity<ErrorMessage> handleNoIdNumberException(
            NoIdNumberException ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler({PersonIdAlreadyExist.class})
    public ResponseEntity<ErrorMessage> handlePatientIdAlreadyExist(
            PersonIdAlreadyExist ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler({EmailAlreadyInUse.class})
    public ResponseEntity<ErrorMessage> handleEmailAlreadyInUse(
            EmailAlreadyInUse ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler({VisitUnavailable.class})
    public ResponseEntity<ErrorMessage> handleVisitUnavailable(
            VisitUnavailable ex) {
        return new ResponseEntity<ErrorMessage>(
                new ErrorMessage(ex.getMessage()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleDefaultException(
            RuntimeException ex) {
        return new ResponseEntity<Object>(
                new ErrorMessage("Unknown Error"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}