package com.mihair.analysis_machine.controller.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionDetails {
    LocalDateTime timestamp;
    String message;
    String details;
    HttpStatus errorCode;

    public ExceptionDetails(LocalDateTime timestamp, String message, String details, HttpStatus errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
}
