package com.online.education.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class TradeFlowAuthentication implements Authentication {

    private List<GrantedAuthority> grantedAuthorities;
    private String username;
    private Long userId;
    private Long companyId;
//    private Long userRoleId;
    private boolean isAuthenticated;

    public TradeFlowAuthentication(String username, Long userId, Long companyId) {
        this.username = username;
        this.userId = userId;
        this.companyId = companyId;
//        this.userRoleId = userRoleId;
        isAuthenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.username;
    }

    public String getUsername() {
        return this.username;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

//    public Long getUserRoleId() {
//        return this.userRoleId;
//    }


}
