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


@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/users").hasRole("ADMIN") // Seuls les admins peuvent voir tous les users
                .requestMatchers("/api/users/{id}").hasAnyRole("USER", "ADMIN") // Tous les users peuvent voir leur profil
                .requestMatchers("/api/users/{id}/delete", "/api/users/{id}/update")
                    .access((authenticationSupplier, context) -> {
                        Authentication authentication = authenticationSupplier.get(); // 🔥 FIX: On récupère Authentication
                        Long userId = Long.valueOf(context.getVariables().get("id"));
                        return new AuthorizationDecision(
                            authentication.getAuthorities().stream()
                                .anyMatch(grantedAuthority -> 
                                    grantedAuthority.getAuthority().equals("ROLE_ADMIN") || 
                                    authentication.getName().equals(userId.toString()) // L'utilisateur peut voir son propre profil
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
