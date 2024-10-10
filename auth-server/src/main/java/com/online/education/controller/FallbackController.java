package com.online.education.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/asFailure")
    public @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE) String asFallback() {
        return "Auth server is currently unavailable";
    }




}
