package com.online.education.security;

import com.online.education.constant.GlobalConstantTokenGeneration;
import com.online.education.entity.TradeFlowUser;
import com.online.education.response.LoginResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTokenGenerator {

    @Autowired
    JwtEncoder accessTokenEncoder;
    @Autowired
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder refreshTokenEncoder;

    @Autowired
    @Qualifier("jwtRefreshTokenDecoder")
    JwtDecoder refreshTokenDecoder;

    @Value("${access_token.validity_period}")
    private int accessTokenValiditySeconds;

    @Value("${refresh_token.validity_period}")
    private int refreshTokenValiditySeconds;


    public LoginResponse createToken(TradeFlowUser user, String uuid) {
        String userTypeId = String.valueOf(user.getUserType()!=null ? user.getUserType().getId() : "");
        Instant now = Instant.now();
        return new LoginResponse(uuid, createAccessToken(user, now, uuid), createRefreshToken(user, now),
                accessTokenValiditySeconds-1, refreshTokenValiditySeconds-1, userTypeId);
    }

    private String createAccessToken(TradeFlowUser user, Instant now, String uuid) {
        return accessTokenEncoder.encode(JwtEncoderParameters.from(
                JwtClaimsSet.builder()
                        .issuer(GlobalConstantTokenGeneration.JWT_ISSUER)
                        .issuedAt(now)
                        .expiresAt(now.plus(accessTokenValiditySeconds, ChronoUnit.SECONDS))
                        .subject(user.getUsername())
                        .claim(GlobalConstantTokenGeneration.COMPANY_ID_KEY, String.valueOf(user.getCompanyId()))
                        .claim(GlobalConstantTokenGeneration.USERNAME_KEY, String.valueOf(user.getUsername()))
                        .claim(GlobalConstantTokenGeneration.USERID_KEY, String.valueOf(user.getId()))
//                        .claim(GlobalConstantTokenGeneration.USER_ROLE_ID, String.valueOf(user.getRoles().stream().findFirst().get().getId()))
                        .claim(GlobalConstantTokenGeneration.USER_TYPE_NAME, String.valueOf(user.getUserType()!=null ? user.getUserType().getName() : Strings.EMPTY))
                        .claim(GlobalConstantTokenGeneration.USER_TYPE_ID, String.valueOf(user.getUserType()!=null ? user.getUserType().getId() : Strings.EMPTY))
                        .claim(GlobalConstantTokenGeneration.UUID_KEY, uuid)
                        .build()
        )).getTokenValue();
    }

    private String createRefreshToken(TradeFlowUser user, Instant now) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(GlobalConstantTokenGeneration.JWT_ISSUER)
                .issuedAt(now)
                .expiresAt(now.plus(refreshTokenValiditySeconds, ChronoUnit.SECONDS))
                .subject(user.getUsername())
                .build();
        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
