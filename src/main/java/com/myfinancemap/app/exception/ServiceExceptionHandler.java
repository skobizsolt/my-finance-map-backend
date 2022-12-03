package com.myfinancemap.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(value = {ServiceExpection.class})
    public ResponseEntity<Object> handleServiceException(final ServiceExpection e) {
        final Error serviceException = new Error(
                e.getMessage()
        );
        return new ResponseEntity<>(serviceException, HttpStatus.BAD_REQUEST);
    }
}
