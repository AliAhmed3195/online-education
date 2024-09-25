package com.online.education.resource;

import com.online.education.request.LoginRequest;
import com.online.education.response.LoginResponse;
import com.online.education.service.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationResource {

    @Autowired
    private UserManager userManager;


    @PostMapping("/oauth/token")
    public LoginResponse getAccessToken(HttpServletRequest httpServletRequest, @RequestBody LoginRequest request) {
        if( request==null || request.getUsername()==null || request.getPassword()==null ) {
            throw new IllegalArgumentException("Username and Password are required");
        }
        return userManager.getAccessTokenByLogin(httpServletRequest, request);
    }
}
