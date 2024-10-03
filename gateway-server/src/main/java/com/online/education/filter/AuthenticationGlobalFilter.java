package com.online.education.filter;

import com.online.education.Repository.OAuthTokenRepository;
import com.online.education.constant.GlobalConstantTokenGeneration;
import com.online.education.entity.OAuthAccessToken;
import com.online.education.validator.RouterValidator;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Claims;

import java.util.List;

@Slf4j
@Component
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered  {

    @Autowired
    private RouterValidator routerValidator;
    @Value("${message}")
    private List<String> openApiEndpoints;

    @Value("${private.secret.key}")
    private String secretKeyString;

    private String secretKeyStrings="e7f3a9c23bfe4d85a28b1cb8d26e82bc234567891011156786";

    @Value("${access_token.expiry.time.in.milliseconds}")
    private String accessTokenExpiryTime;

    @Autowired
    private OAuthTokenRepository oAuthTokenRepository;

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
                String token = getAuthHeader(request);
                Claims claims = verifyToken(token);
                if ( hasAccessTokenExpired(claims.get(GlobalConstantTokenGeneration.USERNAME_KEY).toString())){
                    return this.onError(exchange, "User is un-authorized");
                }
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
    private String getAuthHeader(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if( authorization==null || !authorization.startsWith("Bearer") ) {
            throw new RuntimeException("Authorization missing");
        }else {
            return authorization.replace("Bearer", "").trim();
        }
    }

    private Claims verifyToken( String token ){
        try {
            // Verify the token using the HS256 secret key
            return Jwts.parser()
                    .setSigningKey(secretKeyString.getBytes())  // Use the same secret key used for signing
                    .parseClaimsJws(token)
                    .getBody();  // Extract the claims if token is valid
        } catch (Exception e) {
            log.error("Exception occurred in verifyToken() -- token is invalid", e);
            throw e;  // Rethrow the exception or handle it as necessary
        }
    }


    public Boolean hasAccessTokenExpired(String userName) {
        try {
            Boolean isAccessTokenExpired = false;
            OAuthAccessToken existingTokenEntry = oAuthTokenRepository.findTopByUsernameAndIsActiveTrueOrderByCreatedOnDesc(userName);
            if (existingTokenEntry != null) {
                Long accessTokenExpiry = existingTokenEntry.getModifiedOn().getTime() + Long.valueOf((accessTokenExpiryTime));
                Long currentTimeMillis = System.currentTimeMillis();
                int retval = currentTimeMillis.compareTo(accessTokenExpiry); // compares two Long objects numerically
                if (retval > 0) {
                    isAccessTokenExpired = true;
                    oAuthTokenRepository.inActiveOAuthToken(userName);
                } else if (retval < 0) {
                    log.info("System.currentTimeMillis() is less than accessTokenExpiry");
                }
            }
            return isAccessTokenExpired;
        } catch (Exception e) {
            log.error("Exception occurred in hasAccessTokenExpired() -- token is invalid", e);
            throw e;
        }
    }
}
