package com.mihair.analysis_machine.model.patients.DTO;

public class ModelValidationException extends RuntimeException {
    public ModelValidationException(String message) {
        super(message);
    }
}
