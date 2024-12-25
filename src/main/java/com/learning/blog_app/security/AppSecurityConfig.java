package com.learning.blog_app.security;

import com.learning.blog_app.security.jwt.JwtAuthenticationFilter;
import com.learning.blog_app.security.jwt.JwtAuthenticationManager;
import com.learning.blog_app.security.jwt.JwtService;
import com.learning.blog_app.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public AppSecurityConfig(JwtService jwtService, UserService userService) {
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(new JwtAuthenticationManager(jwtService,userService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO: in prod, CORS and CSRF shouldn't be blanket disabled
        http.cors().disable().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/hello").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET,"/users").permitAll()
                //.antMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                .antMatchers("/*/**").authenticated()
                .and()
               // .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
//                .addFilterBefore(sstAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        super.configure(http);
    }
}
