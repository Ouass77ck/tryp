package com.tryp.backend.controller;

import com.tryp.backend.model.Utilisateur;
import com.tryp.backend.service.UtilisateurService;
import com.tryp.backend.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // 🔹 register by id
    @PostMapping("/register")
    public ResponseEntity<Utilisateur> registerUser(@RequestBody RegisterRequest request) {
        Utilisateur utilisateur = utilisateurService.createUser(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPictureUrl()
        );
        return ResponseEntity.ok(utilisateur);
    }

    // 🔹 get all user
    @GetMapping
    public List<Utilisateur> getAllUsers() {
        return utilisateurService.getAllUsers();
    }

    // 🔹 get one user by id
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
        return utilisateurService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 🔹 update user
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request) {
        return utilisateurService.updateUser(id, request.getEmail(), request.getPassword(), request.getName(), request.getPictureUrl())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return utilisateurService.deleteUser(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
     
}
