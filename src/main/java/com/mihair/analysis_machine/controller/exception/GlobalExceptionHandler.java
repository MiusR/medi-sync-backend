package com.mihair.analysis_machine.controller.exception;

import com.azure.core.exception.ResourceNotFoundException;
import com.mihair.analysis_machine.model.patients.DTO.ModelValidationException;
import com.mihair.analysis_machine.service.exception.CryptoClientCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.MissingResourceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFound(ResourceNotFoundException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), "Could not find requested resource!", ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingResourceException.class)
    public ResponseEntity<ExceptionDetails> handleResourceMissing(MissingResourceException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), "Could not find requested resource!", ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CryptoClientCreationException.class)
    public ResponseEntity<ExceptionDetails> handleCryptographyClientError(CryptoClientCreationException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), "Could not create a secure client for the request! Operation ABORTED!", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModelValidationException.class)
    public ResponseEntity<ExceptionDetails> handleDTOValidation(ModelValidationException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), "Could not interpret provided data! Fields missing or in wrong format!", ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleEverything(Exception ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), "Unknown error has occurred! Please try again or contact server admin!", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
