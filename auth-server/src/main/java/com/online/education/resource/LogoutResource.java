package com.online.education.resource;

import com.online.education.Repository.OAuthTokenRepository;
import com.online.education.constant.GlobalConstantTokenGeneration;
import com.online.education.entity.OAuthAccessToken;
import com.online.education.response.GenericResponse;
import com.online.education.response.LogoutResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LogoutResource {

    @Autowired
    OAuthTokenRepository oAuthTokenRepository;

    @Autowired
    private Environment environment;

    @PostMapping(path = "/logout")
    public GenericResponse logout(HttpServletRequest request){
        final String username = request.getHeader(GlobalConstantTokenGeneration.USERNAME_KEY);
        final String uuid = request.getHeader(GlobalConstantTokenGeneration.UUID_KEY);
        Date logoutDate = new Date();
        OAuthAccessToken loginDate = oAuthTokenRepository.findTopByUsernameAndIsActiveTrueOrderByCreatedOnDesc(username);

        //remove from OAUTH_ACCESS_TOKEN on the basis of username
        oAuthTokenRepository.inActiveOAuthToken(username);
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setLoginTime(com.online.education.util.DateUtils.dateToString(loginDate.getCreatedOn(),environment.getProperty("date.format.with.time")));
        logoutResponse.setLogoutTime(com.online.education.util.DateUtils.dateToString(logoutDate,environment.getProperty("date.format.with.time")));
        logoutResponse.setDuration(com.online.education.util.DateUtils.getDuration(loginDate.getCreatedOn(),logoutDate));

        return GenericResponse.createSuccessResponse(environment.getProperty("message.logout.success"),"logoutInformation",logoutResponse);
    }
}
