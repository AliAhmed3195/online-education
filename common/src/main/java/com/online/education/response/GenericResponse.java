package com.online.education.response;

import lombok.Getter;

import java.util.Map;

@Getter
public class GenericResponse {

    private String message;
    private String error;
    private Integer status;
    private Map<String, Object> data;

    private static final String SUCCESS_MESSAGE = "Success";

    public GenericResponse(String message, String error, Integer status) {
        this.message = message;
        this.error = error;
        this.status = status;
    }

    public static GenericResponse createErrorResponse(String error) {
        return new GenericResponse(null, error, 0);
    }
}
