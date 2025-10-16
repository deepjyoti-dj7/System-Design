package com.airline.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // updated syntax
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health").permitAll() // allow /health without login
                        .anyRequest().authenticated()          // everything else requires authentication
                )
                .httpBasic(); // or JWT if using token-based auth

        return http.build();
    }
}
