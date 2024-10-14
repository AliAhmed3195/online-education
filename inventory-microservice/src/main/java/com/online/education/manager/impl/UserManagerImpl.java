package com.online.education.manager.impl;

import com.online.education.Repository.TradeFlowUserRepository;
import com.online.education.entity.TradeFlowUser;
import com.online.education.exception.UserServiceException;
import com.online.education.manager.UserManager;
import com.online.education.request.ChangePasswordRequestDTO;
import com.online.education.response.ChangePasswordResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Component("userManagerUserServiceImpl")
public class UserManagerImpl implements UserManager {

    @Autowired
    private TradeFlowUserRepository tradeFlowUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${general.invalid.user}")
    private String invalidUserError;


    @Override
    public ChangePasswordResponseDTO changePassword(HttpServletRequest request, @Valid ChangePasswordRequestDTO requestDTO, String username) throws UserServiceException {
        Optional<TradeFlowUser> tradeFlowUser = tradeFlowUserRepository.findByUsernameAndIsActiveTrue( username );
        if ( tradeFlowUser.isPresent() ) {
            TradeFlowUser tradeFlowUserObject = tradeFlowUser.get();
            tradeFlowUserObject.setPassword( passwordEncoder.encode( requestDTO.getNewPassword() ) );
            tradeFlowUserObject.setLastPasswordResetDate( new Date() );
            tradeFlowUserRepository.save( tradeFlowUserObject );
            return new ChangePasswordResponseDTO(username, requestDTO.getNewPassword());

        } else {
            log.error("changePassword user not found: {}", username);
            throw new IllegalArgumentException(invalidUserError);
        }
    }
}
