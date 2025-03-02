package com.tryp.backend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "mySecretKeyForJWTAdvibijzvbhdvbjvdbjbjdvbjdvjjdhbjdjhbdvdvbhdjbhdjvdjdvbjbdvjbhjbjbjbbjhbjhhjbhjjhbbjhbjhjbhbhjbjbhbjbjhjbhbjuthenticationmySecretKeyForJWT";
    private static final long EXPIRATION_TIME = 86400000; // 1 jour

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String email) {
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
