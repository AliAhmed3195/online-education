package com.online.education.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends Exception {

    private int errorCode;
    public UserServiceException(String message, int errorCode){
        super(message);
    }
}
