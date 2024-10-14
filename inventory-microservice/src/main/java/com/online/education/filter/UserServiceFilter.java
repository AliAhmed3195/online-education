package com.online.education.filter;

import com.online.education.constant.GlobalConstantTokenGeneration;
import com.online.education.util.Converter;
import com.online.education.validator.RouterValidator;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@Order(1)
@Slf4j
public class UserServiceFilter implements Filter {

    @Autowired
    private RouterValidator routerValidator;

    @Value("${user.microservice.swagger.permission}")
    private Boolean swaggerPermission;

    @Value("${error.message.permission.denied}")
    private String permissionDeniedErrorMessage;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if(!routerValidator.isSecured(req.getRequestURI()) || req.getRequestURI().contains("v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        } else if (swaggerPermission && req.getRequestURI().contains("swagger")) {
            chain.doFilter(request, response);
        }
        String username = req.getHeader(GlobalConstantTokenGeneration.USERNAME_KEY);
        String userId = req.getHeader(GlobalConstantTokenGeneration.USERID_KEY);
        String companyId = req.getHeader(GlobalConstantTokenGeneration.COMPANY_ID_KEY);
//        String userRoleId = req.getHeader(GlobalConstantTokenGeneration.USER_ROLE_ID);

        boolean hasPermission = false;
        if( StringUtils.isNotBlank(username) && StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(companyId)
             ) {
            try {

                if( hasPermission(req.getRequestURI()) ) {
                    hasPermission = true;
                    SecurityContextHolder.getContext().setAuthentication(new TradeFlowAuthentication(username,
                            Long.parseLong(userId), Long.parseLong(companyId)));
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        if( hasPermission ) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            res.getWriter().write(Converter.toJson(new HashMap(){{
                put("message", permissionDeniedErrorMessage);
            }}));
        }
    }

    private boolean hasPermission(String uri) {
//        return permissionService.getPermissionUriList(userRoleId)
//                .stream().anyMatch(permissionUriView-> permissionUriView.getUri().equalsIgnoreCase(uri));
        return true;
    }
}
