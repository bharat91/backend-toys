package com.learning.blog_app.security.jwt;

import com.learning.blog_app.dto.UserDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication implements Authentication {

    private String jwtString;
    private UserDetailsDto user;

    public JwtAuthentication(String jwtString) {
        this.jwtString = jwtString;
    }

    void setUser(UserDetailsDto userDetailsDto){
        this.user = userDetailsDto;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getCredentials() {
        return jwtString;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public UserDetailsDto getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return (user != null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
