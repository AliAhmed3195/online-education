package com.online.education.service;

import com.online.education.request.LoginRequest;
import com.online.education.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface UserManager {
    LoginResponse getAccessTokenByLogin(HttpServletRequest httpServletRequest, LoginRequest request);
}
