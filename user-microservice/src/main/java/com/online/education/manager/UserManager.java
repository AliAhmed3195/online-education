package com.online.education.manager;

import com.online.education.entity.TradeFlowUser;
import com.online.education.exception.UserServiceException;
import com.online.education.request.*;
import com.online.education.response.ChangePasswordResponseDTO;
import com.online.education.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface UserManager {

    ChangePasswordResponseDTO changePassword(HttpServletRequest request, @Valid ChangePasswordRequestDTO requestDTO, String username) throws UserServiceException;

    GenericResponse createUser(TradeFlowUserRequestDto user);

    GenericResponse userList(UserSearchRequest userSearchRequest);

    GenericResponse findByUserId(UserIdRequest userIdRequest);

    GenericResponse updateUserDetails(TradeFlowUser user);

    GenericResponse listBusinessRole(BusinessRoleSearchRequest roleSearchRequest);

    public GenericResponse addBusinessRole(BusinessRoleRequestDto roleRequestDTO);
}
