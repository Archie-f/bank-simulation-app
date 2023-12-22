package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.authorizeRequests()
                //Admin user can access pages related to accounts. (all endpoints after /account endpoint)
                .antMatchers("/account/**").hasAuthority("Admin")
                //Admin and Cashier users can access pages related to transaction. (all endpoints after /transaction endpoint)
                .antMatchers("/transaction/**").hasAnyAuthority("Admin","Cashier")
                //Everyone can access login or default pages. (end points /login or /)
                .antMatchers("/login","/")
                .permitAll()
                //any other request that we don't cover here needs to be authenticated
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authSuccessHandler) //landing pages for admin and cashier
                .failureUrl("/login?error=true")
                .permitAll()
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
            .and()
                .rememberMe()
                .tokenValiditySeconds(300)
                .key("bankapp")
                .userDetailsService(securityService)
                .and().build();
    }
}
