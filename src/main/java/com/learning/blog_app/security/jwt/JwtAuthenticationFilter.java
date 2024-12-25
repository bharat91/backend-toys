package com.learning.blog_app.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

public class JwtAuthenticationFilter extends AuthenticationFilter {
    public JwtAuthenticationFilter(JwtAuthenticationManager authenticationManager) {


        super(authenticationManager, new JwtAuthenticationConverter());
    }
}
