package com.online.education.resource;

import com.online.education.exception.UserServiceException;
import com.online.education.filter.TradeFlowAuthentication;
import com.online.education.manager.UserManager;
import com.online.education.request.ChangePasswordRequestDTO;
import com.online.education.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("userManagerUserServiceImpl")
    private UserManager userManager;

    @PostMapping("/password/change")
    public GenericResponse changePassword(HttpServletRequest request, @Valid @RequestBody ChangePasswordRequestDTO requestDTO) throws UserServiceException {
        TradeFlowAuthentication authentication = (TradeFlowAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return GenericResponse.createSuccessResponse(environment.getProperty("message.changed.user-password", "Successfully changed user password"),
                "result", userManager.changePassword(request, requestDTO, authentication.getUsername()));
    }
}
