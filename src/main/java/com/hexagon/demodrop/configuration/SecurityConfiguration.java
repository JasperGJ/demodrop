package com.hexagon.demodrop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userService;

    public SecurityConfiguration(UserDetailsService userService) {
        this.userService = userService;
    }

    public DaoAuthenticationProvider authenticationProvider(){
        System.out.println("configure(DaoAuthenticationProvider() was called");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        System.out.println("configure(AuthenticationManagerBuilder auth) was called");
        auth.authenticationProvider(authenticationProvider());
    }

}
