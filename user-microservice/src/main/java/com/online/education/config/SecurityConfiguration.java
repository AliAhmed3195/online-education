package com.online.education.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration  {

    @Value("${auth-server.public.uris}")
    private String[] publicUris;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        http
//                .csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/**", "/open/api/**", "/auth/**", "/v3/api-docs/**", "/swagger-ui/index.html",
//                                "/swagger-ui.html", "/configuration/ui", "/swagger-resources/**","/swagger-ui/**","/v3/api-docs/**",
//                                "/v3/api-docs/public/**","/user-microservice/**").permitAll()
//                        .anyRequest().authenticated());
//
//        return http.build();

        return httpSecurity.
                authorizeHttpRequests(authorize -> authorize
                        // allow all swagger uris
                        .requestMatchers("/api/**","/v2/api-docs", "/v3/api-docs/**", "/swagger-ui.html", "/configuration/ui", "/swagger-resources/**",
                                "/configuration/security", "/webjars/**", "/swagger-ui/**","/swagger-ui/index.html").permitAll()
                        .anyRequest().authenticated())
//        authorizeHttpRequests(authorize -> authorize
//                .anyRequest().permitAll()  // Allow all requests without authentication
//        )
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

    // If you need the AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
}
