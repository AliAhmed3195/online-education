package com.online.education.exception;

import com.online.education.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler( value = AuthServiceException.class)
    public ResponseEntity<?> handleAuthServiceException(AuthServiceException ex) {
        return new ResponseEntity<>(GenericResponse.createErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
