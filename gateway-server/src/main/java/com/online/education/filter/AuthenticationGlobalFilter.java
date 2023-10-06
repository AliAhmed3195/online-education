package com.online.education.filter;

import com.online.education.validator.RouterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationGlobalFilter {

    @Autowired
    private RouterValidator routerValidator;

    @Value("${security.bypass.uris}")
    private List<String> openApiEndpoints;

    @Value("${gateway.server.swagger.permission}")
    private Boolean swaggerPermission;
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
//        if ( routerValidator.isSecured( request ) ) {
//            if( isAuthMissing( request ) ) {
//                return this.onError(exchange, "User is un-authorized");
//            }
//            try {
//
//            } catch ( Exception e ) {
//
//            }
//        }
//        return null;
//    }
//    private boolean isAuthMissing( ServerHttpRequest request ) {
//        return !request.getHeaders().containsKey("Authorization");
//    }
//    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
////        try {
////            response.writeWith(Flux.just(exchange.getResponse().bufferFactory().wrap(
////                    Converter.toJson(new HashMap(){{
////                        put("message", errorMessage);
////                    }}).getBytes(StandardCharsets.UTF_8))));
////        } catch (JsonProcessingException e) {
////            // ignore exception
////        }
//        return response.setComplete();
//    }
}
