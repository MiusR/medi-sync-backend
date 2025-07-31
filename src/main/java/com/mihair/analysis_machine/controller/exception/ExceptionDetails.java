package com.mihair.analysis_machine.controller.exception;

import java.time.LocalDateTime;

public record ExceptionDetails(LocalDateTime timestamp,
                               String message,
                               String details,
                               ControllerErrorCodes errorCode) {
}
