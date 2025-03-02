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

   public String generateToken(Long id, String role) {
    return Jwts.builder()
            .setSubject(Long.toString(id))
            .claim("role", "ROLE_" + role)  // Ajout du préfixe ROLE_
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}

    // Méthode pour extraire le rôle
    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }


    // Extraire l'id depuis le token
    public String extractId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Vérifier si le token est valide
    public boolean validateToken(String token, Long id) {
        return id.equals(extractId(token)) && !isTokenExpired(token);
    }

    // Vérifier si le token est expiré
    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
