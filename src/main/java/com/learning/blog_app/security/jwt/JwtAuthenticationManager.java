package com.learning.blog_app.security.jwt;

import com.learning.blog_app.dto.UserDetailsDto;
import com.learning.blog_app.entity.UserEntity;
import com.learning.blog_app.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationManager implements AuthenticationManager {

    private JwtService jwtService;
    private UserService userService;

    public JwtAuthenticationManager(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof JwtAuthentication){
            JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;

            String jwtString = jwtAuthentication.getCredentials();
            String username = jwtService.getUsernameFromJwt(jwtString);
            // TODO: crypto failure on jwt verification
            // TODO: check if jwt is expired

            UserDetailsDto user = userService.findUserByUsername(username);
            jwtAuthentication.setUser(user);
            return jwtAuthentication;
        }
        return null;
    }
}
