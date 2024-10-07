package com.online.education.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@EnableWebSecurity
public class WebSecurity {

    @Autowired
    JwtTokenConverter jwtTokenConverter;

    @Value("${auth-server.public.uris}")
    private String[] publicUris;

    @Value("${private.secret.key}")
    private String secretKeyString;
    // Replace this with your actual simple key (shared symmetric key)

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
                .cors().and()  // Enable CORS here
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
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3030")); // Allow the gateway origin
        config.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);  // Cache the preflight response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
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
        // Create a SecretKey for HS256
        SecretKey secretKey = new SecretKeySpec(secretKeyString.getBytes(), "HmacSHA256");
        // Create OctetSequenceKey (for symmetric algorithms like HS256)
        OctetSequenceKey octetKey = new OctetSequenceKey.Builder(secretKey.getEncoded())
//                .algorithm(JWSAlgorithm.HS256) // Specify HS256 in the header
                .build();
        // Create JwtEncoder with OctetSequenceKey for HS256
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(octetKey)));
    }

    // Modify your token generation logic to explicitly set the JwsHeader with HS256
//    @Bean
//    public JwsHeader jwsHeader() {
//        return JwsHeader.with(MacAlgorithm.HS256).build(); // HS256 explicitly using Spring Security's JwsAlgorithms
//    }

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
        OctetSequenceKey octetKey = new OctetSequenceKey.Builder(secretKey.getEncoded())
//                .algorithm(JWSAlgorithm.HS256) // Specify HS256 in the header
                .build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(octetKey)));
    }

    // Helper method to create a SecretKey from a string
    private SecretKey generateSecretKey(String secret) {
        return new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    }

}
