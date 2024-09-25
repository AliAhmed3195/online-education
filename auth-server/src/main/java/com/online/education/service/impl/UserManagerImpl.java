package com.online.education.service.impl;

import com.online.education.Repository.OAuthTokenRepository;
import com.online.education.Repository.TradeFlowUserRepository;
import com.online.education.entity.OAuthAccessToken;
import com.online.education.entity.TradeFlowUser;
import com.online.education.exception.AuthServiceException;
import com.online.education.request.LoginRequest;
import com.online.education.response.LoginResponse;
import com.online.education.security.JwtTokenGenerator;
import com.online.education.service.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialClob;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
public class UserManagerImpl implements UserManager {

    @Value("${general.invalid.user}")
    private String invalidUserError;

    @Autowired
    private TradeFlowUserRepository tradeFlowUserRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    OAuthTokenRepository oAuthTokenRepository;

    @Override
    public LoginResponse getAccessTokenByLogin( HttpServletRequest httpServletRequest, LoginRequest request ) {
        if(isValidUserType(httpServletRequest, request.getUsername())){
            log.info("Invalid User or User Type for user : {} ", request.getUsername());
            throw new AuthServiceException(invalidUserError);
        }
        // validate username and password
        Authentication authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword()));
        String uuid = UUID.randomUUID().toString();
        // create new token and provide response
        LoginResponse response = jwtTokenGenerator.createToken((TradeFlowUser) authentication.getPrincipal(), uuid);
        // save token in database
        try {
            oAuthTokenRepository.save(new OAuthAccessToken(null, request.getUsername(),
                    new SerialClob(response.getAccessToken().toCharArray()),
                    new SerialClob(response.getRefreshToken().toCharArray()), null, true, uuid));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
    private boolean isValidUserType(HttpServletRequest httpServletRequest,String userName){

        Optional<TradeFlowUser> tradeFlowUser = tradeFlowUserRepository.findByUsernameAndIsActiveTrue( userName );
        if (!tradeFlowUser.isPresent()) {
            log.error("No user Exist with user name : {}", userName.toLowerCase());
            throw new AuthServiceException(invalidUserError);
        }
//        try {
//            Map<String, String> map = new HashMap<String, String>();
//            Enumeration headerNames = httpServletRequest.getHeaderNames();
//            while (headerNames.hasMoreElements()) {
//                String key = (String) headerNames.nextElement();
//                String value = httpServletRequest.getHeader(key);
//                map.put(key, value);
//            }
//            String requestedUserType = map.get(GlobalConstants.USER_TYPE_NAME);
//            AmbitUser ambitUser = userOptional.get();
//            if(!requestedUserType.isBlank() && requestedUserType.equalsIgnoreCase(ambitUser.getUserType().getName())){
//                return true;
//            }
//        } catch (Exception e){
//            log.error("Exception occurs : {}", e);
//        }
        return false;
    }
}
