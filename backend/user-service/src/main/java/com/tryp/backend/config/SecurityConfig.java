package com.tryp.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Désactiver CSRF pour faciliter les tests
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll() // Autoriser les endpoints API sans authentification
                .anyRequest().authenticated()
            )
            .formLogin(login -> login.disable()) // Désactiver le formulaire de connexion
            .httpBasic(basic -> basic.disable()); // Désactiver l'authentification Basic

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

