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
                // configuring second route, for user microservice
                .route("user-microservice", r -> r.path("/user-microservice/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("usFailure").setFallbackUri("fallback:/usFailure")))
                        .uri("lb://user-microservice/"))
                // configuring third route, for inventory microservice
                .route("inventory-microservice", r -> r.path("/inventory-microservice/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("inventoryFailure").setFallbackUri("fallback:/inventoryFailure")))
                        .uri("lb://inventory-microservice/"))
                // configuring fourth route, for order processing microservice
                .route("order-processing-microservice", r -> r.path("/order-processing-microservice/**")
                .filters(f -> f.circuitBreaker(c -> c.setName("orderFailure").setFallbackUri("fallback:/orderFailure")))
                .uri("lb://order-processing-microservice/"))
                . build();


    }
}
