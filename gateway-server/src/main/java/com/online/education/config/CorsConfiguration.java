package com.online.education.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfiguration extends org.springframework.web.cors.CorsConfiguration {

    @Value("${gateway.cors.allowed.origins}")
    private String allowedOrigins;

    @Value("${gateway.cors.allowed.methods}")
    private String allowedMethods;

    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);  // Allow credentials if needed
        corsConfig.setMaxAge(3600L);  // Cache the preflight request for 1 hour
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsWebFilter(source);
    }
}
