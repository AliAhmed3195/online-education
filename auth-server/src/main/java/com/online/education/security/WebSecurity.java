package com.online.education.security;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.jcajce.provider.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurity {

    @Autowired
    JwtTokenConverter jwtTokenConverter;

    @Value("${auth-server.public.uris}")
    private String[] publicUris;


    // Replace this with your actual simple key (shared symmetric key)
    private final String secretKeyString = "e7f3a9c23bfe4d85a28b1cb8d26e82bc";

    // Refresh token key (different from access token for added security)
    private final String refreshTokenSecret = "c2f5a4c25dae4d6da35f1b6fd69e75ab";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
                authorizeHttpRequests(authorize -> authorize.requestMatchers(publicUris).permitAll()
                        // allow all swagger uris
                        .requestMatchers("/v2/api-docs", "/v3/api-docs/**", "/swagger-ui.html", "/configuration/ui", "/swagger-resources/**",
                                "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated())
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
//                .oauth2ResourceServer(oauth2 ->
//                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtTokenConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                ).build();
    }


    @Bean
    @Primary
    public JwtDecoder jwtAccessTokenDecoder() {
        SecretKey secretKey = generateSecretKey(secretKeyString);
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    @Primary
    public JwtEncoder jwtAccessTokenEncoder() {
        SecretKey secretKey = generateSecretKey(secretKeyString);
        OctetSequenceKey octetKey = new OctetSequenceKey.Builder(secretKey.getEncoded()).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(octetKey)));
    }




    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    public JwtDecoder jwtRefreshTokenDecoder() {
        SecretKey secretKey = generateSecretKey(refreshTokenSecret);
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    public JwtEncoder jwtRefreshTokenEncoder() {
        SecretKey secretKey = generateSecretKey(refreshTokenSecret);
        OctetSequenceKey octetKey = new OctetSequenceKey.Builder(secretKey.getEncoded()).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(octetKey)));
    }


    // Helper method to create a SecretKey from a string
    private SecretKey generateSecretKey(String secret) {
        return new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    }

//    @Bean
//    @Primary
//    public JwtDecoder jwtAccessTokenDecoder() {
//        return NimbusJwtDecoder.withPublicKey(keyUtils.getAccessTokenPublicKey()).build();
//    }
//
//    @Bean
//    @Primary
//    public JwtEncoder jwtAccessTokenEncoder() {
//        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(new RSAKey.Builder(keyUtils.getAccessTokenPublicKey())
//                .privateKey(keyUtils.getAccessTokenPrivateKey()).build())));
//    }
//    private final String secretKey = "e7f3a9c23bfe4d85a28b1cb8d26e82bc";

}
