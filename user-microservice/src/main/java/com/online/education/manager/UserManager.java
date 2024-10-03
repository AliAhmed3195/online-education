package com.online.education.manager;

import com.online.education.exception.UserServiceException;
import com.online.education.request.ChangePasswordRequestDTO;
import com.online.education.response.ChangePasswordResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface UserManager {

    ChangePasswordResponseDTO changePassword(HttpServletRequest request, @Valid ChangePasswordRequestDTO requestDTO, String username) throws UserServiceException;
}
