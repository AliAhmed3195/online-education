package com.online.education.security;

import com.online.education.Repository.OAuthTokenRepository;
import com.online.education.Repository.TradeFlowUserRepository;
import com.online.education.entity.OAuthAccessToken;
import com.online.education.entity.TradeFlowUser;
import com.online.education.exception.AuthServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@Primary
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private TradeFlowUserRepository tradeFlowUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    OAuthTokenRepository oAuthTokenRepository;

    @Value("${general.invalid.user}")
    private String invalidUserError;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Validating user {}", authentication.getPrincipal().toString().toLowerCase());
        Optional<TradeFlowUser> userOptional = tradeFlowUserRepository.findByUsernameAndIsActiveTrue(authentication.getPrincipal().toString().toLowerCase());
        if (!userOptional.isPresent()) {
            throw new AuthServiceException(invalidUserError);
        }
        TradeFlowUser user = userOptional.get();
        boolean authenticated;
        authenticated = passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword());

        OAuthAccessToken oAuthAccessToken = oAuthTokenRepository.findTopByUsernameAndIsActiveTrueOrderByCreatedOnDesc(authentication.getPrincipal().toString());
//        if(!Objects.isNull(oAuthAccessToken) && !Objects.isNull(oAuthAccessToken.getUuid()) && !oAuthAccessToken.getUuid().isEmpty()) {
//            ambitRedisCache.remove(oAuthAccessToken.getUuid(), UserLoginInfo.class);
//        }
        oAuthTokenRepository.inActiveOAuthToken(authentication.getPrincipal().toString());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.EMPTY_LIST);
    }
}
