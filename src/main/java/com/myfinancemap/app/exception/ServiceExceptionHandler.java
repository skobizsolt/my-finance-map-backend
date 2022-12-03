package com.myfinancemap.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.myfinancemap.app.exception.Error.FORBIDDEN_BEHAVIOUR;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceExpection.class)
    public ResponseEntity<Object> handleServiceException(final ServiceExpection e) {
        final Error serviceException = new Error(
                e.getMessage()
        );
        return new ResponseEntity<>(serviceException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException() {
        Error errorDetails = new Error(FORBIDDEN_BEHAVIOUR);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
}
