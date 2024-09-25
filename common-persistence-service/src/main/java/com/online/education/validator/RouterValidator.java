package com.online.education.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouterValidator {

    @Value("${security.bypass.uris}")
    private List<String> openApiEndpoints;

    @Value("${gateway.server.swagger.permission}")
    private Boolean swaggerPermission;

    public boolean isSecured(ServerHttpRequest request){
        return isProceed(request.getURI().getPath());
    }

    public boolean isProceed(String request){
        if( swaggerPermission && request.contains("/swagger-") ) {
            return false;
        }
        for( String openApi: openApiEndpoints ){
            if(openApi.contains(request))
                return false;
        }
        return true;
    }
}
