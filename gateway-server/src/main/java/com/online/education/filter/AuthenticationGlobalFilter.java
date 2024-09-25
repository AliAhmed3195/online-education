package com.online.education.filter;

import com.online.education.validator.RouterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered  {

    @Autowired
    private RouterValidator routerValidator;
//
    @Value("${message}")
    private List<String> openApiEndpoints;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationGlobalFilter.class);

    // This method or constructor is called when the class is initialized
    @Autowired
    public void init() {
        // Log the value to check if it is correctly loaded
        logger.info("Loaded Open API Endpoints from properties: {}", openApiEndpoints);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();  // This is the reactive type

        if (routerValidator.isSecured(request)) {
            if (isAuthMissing(request)) {
                return this.onError(exchange, "User is un-authorized");
            }
            try {
                // Perform any authentication or authorization logic here
            } catch (Exception e) {
                // Handle exceptions if necessary
                return this.onError(exchange, "User is un-authorized");
            }
        } else {
            // Mutate the request to remove the Authorization header if not secured
                request.mutate().headers(httpHeaders -> {
                    httpHeaders.remove("Authorization");
                });
        }
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {}));
    }
    private boolean isAuthMissing( ServerHttpRequest request ) {
        return !request.getHeaders().containsKey("Authorization");
    }
    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        try {
//            response.writeWith(Flux.just(exchange.getResponse().bufferFactory().wrap(
//                    Converter.toJson(new HashMap(){{
//                        put("message", errorMessage);
//                    }}).getBytes(StandardCharsets.UTF_8))));
//        } catch (JsonProcessingException e) {
//            // ignore exception
//        }
        return response.setComplete();
    }
}
