package com.hexagon.demodrop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure(HttpSecurity http) was called");
        http.cors().and().csrf().disable()
                .httpBasic()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                .and()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET, "/index.html","/register","/static/**", "/*.js","/*.png", "/*.json", "/*.ico")
                .permitAll()
                .antMatchers(
                        HttpMethod.POST, "/register","/confirm","/change","/changepassword")
                .permitAll()
                .antMatchers(
                        HttpMethod.GET, "/confirm","/reset","/confirmreset","/change")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/index.html")
                .permitAll()
                .loginProcessingUrl("/login")
                .successForwardUrl("/loginSucces")
                .failureForwardUrl("/loginFailure")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .invalidateHttpSession(true);
    }

}
