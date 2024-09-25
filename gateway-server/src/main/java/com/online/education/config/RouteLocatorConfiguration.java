package com.online.education.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // configuring second route, for auth server
                .route("auth-server", r -> r.path("/auth-server/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("asFailure").setFallbackUri("fallback:/asFailure")))
                        .uri("lb://auth-server/"))
                .build();
    }
}
