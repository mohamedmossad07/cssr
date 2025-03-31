package org.exalt.cssr.exceptions.handlers;

import org.exalt.cssr.exceptions.ApiRequestException;
import org.exalt.cssr.responses.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception Handler to handle exceptions gracefully
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handler to API Client request exceptions
     *
     * @param exception accepting the thrown exception as a param
     * @return FailureResponse object as a response
     */
    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleDuplicateEntryExceptions(ApiRequestException exception) {
        return new ResponseEntity<>(
                new FailureResponse(exception.getMessage()),
                exception.getStatus());
    }

    /**
     * Backup exception handler for unhandled exceptions
     *
     * @param exception accepting the thrown exception as a param
     * @return FailureResponse object as a response
     */
    @ExceptionHandler
    public ResponseEntity<FailureResponse> handleAllExceptions(Exception exception) {
        return new ResponseEntity<>(
                new FailureResponse(exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
