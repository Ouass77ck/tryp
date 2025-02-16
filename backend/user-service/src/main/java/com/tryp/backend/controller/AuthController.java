package com.tryp.backend.controller;

import com.tryp.backend.dto.LoginRequest;
import com.tryp.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 🔹 user authentification
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<String> token = authService.authenticate(request.getEmail(), request.getPassword());

        return token.map(t -> ResponseEntity.ok(Map.of("token", t)))
            .orElse(ResponseEntity.status(401).body(Map.of("error", "Identifiants invalides")));

    }
}
