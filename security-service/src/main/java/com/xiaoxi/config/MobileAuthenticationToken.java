package com.xiaoxi.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author： momo
 *
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;
    private Object credentials;
    private String phone;
    private String mobileCode;


    public MobileAuthenticationToken(String phone, String mobileCode) {
        super(null);
        this.phone = phone;
        this.mobileCode = mobileCode;
        this.setAuthenticated(false);
    }

    public MobileAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
