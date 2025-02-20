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
            // Ajoute ton JwtFilter avant UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/users").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/users/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers("/api/users/{id}/delete", "/api/users/{id}/update")
                    .access((authenticationSupplier, context) -> {
                        Authentication authentication = authenticationSupplier.get();
                        Long userId = Long.valueOf(context.getVariables().get("id"));
                        return new AuthorizationDecision(
                            authentication.getAuthorities().stream()
                                .anyMatch(grantedAuthority -> 
                                    grantedAuthority.getAuthority().equals("ROLE_ADMIN") || 
                                    authentication.getName().equals(userId.toString())
                                )
                        );
                    })
                .anyRequest().authenticated()
            )
            .formLogin(login -> login.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
