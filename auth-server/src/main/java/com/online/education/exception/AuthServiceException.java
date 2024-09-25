package com.online.education.exception;


import org.springframework.security.core.AuthenticationException;

public class AuthServiceException extends AuthenticationException {

    public AuthServiceException(String message) {
        super(message);
    }

    public AuthServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
