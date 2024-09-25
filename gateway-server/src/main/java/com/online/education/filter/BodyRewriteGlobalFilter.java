package com.online.education.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class BodyRewriteGlobalFilter implements GlobalFilter, Ordered {

//    @Autowired
//    private ModifyRequestBodyGatewayFilterFactory modifyRequestBodyFilter;
//
//    @Autowired
//    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyFactory;
//
//    @Autowired
//    RequestBodyRewrite requestBodyRewrite;

//    @Autowired
//    ResponseBodyRewrite responseBodyRewrite;


    @Override
    public int getOrder() {
        // second filter
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String uri = exchange.getRequest().getURI().getPath();

        if( uri.contains("swagger") || uri.contains("v2/api-docs") || uri.contains("v3/api-docs") ||
                (exchange.getRequest().getHeaders().getContentType() !=null &&
                        exchange.getRequest().getHeaders().getContentType().includes(MediaType.MULTIPART_FORM_DATA))) {
            return chain.filter(exchange);
        }
     /* Below code to modify HTTP requests Only
            in a gateway using body Rewrite Function
            and return decrypted request body to down stream */
//        return modifyRequestBodyFilter
//                .apply(new ModifyRequestBodyGatewayFilterFactory.Config()
//                        .setRewriteFunction(String.class, String.class, requestBodyRewrite))
//                .filter(exchange, chain);
        return chain.filter(exchange);

    }
}
