package com.tryp.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/voyages").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/voyages/creation").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers("/api/voyages/{id}/get").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                //.requestMatchers("/api/voyages/{id}/delete").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                //.requestMatchers("/api/voyages/{id}/put").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers("/api/voyages/{id}/delete", "/api/voyages/{id}/update").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
