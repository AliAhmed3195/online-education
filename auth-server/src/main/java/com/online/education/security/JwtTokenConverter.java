package com.online.education.security;

import com.online.education.entity.TradeFlowUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JwtTokenConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        TradeFlowUser user = new TradeFlowUser();
        user.setUsername(jwt.getSubject());
        return new UsernamePasswordAuthenticationToken(user, jwt, Collections.emptyList());
    }
}
