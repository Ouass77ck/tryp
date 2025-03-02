package com.tryp.backend.controller;

import com.tryp.backend.model.Utilisateur;
import com.tryp.backend.model.Role;
import com.tryp.backend.service.UtilisateurService;
import com.tryp.backend.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // 🔹 inscription accessible a tlm
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

    // 🔹 seuls les admins peuvent voir tous les users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUsers(Authentication authentication) {
            return ResponseEntity.ok(utilisateurService.getAllUsers());
    }

    // 🔹 accessible à tt les users
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id, Authentication authentication) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUserById(id);
        if (utilisateur.isPresent() && (authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ||
            authentication.getName().equals(utilisateur.get().getIdUser().toString()))) {
            return ResponseEntity.ok(utilisateur.get());
        }
        return ResponseEntity.status(403).build();
    }

    // 🔹 un user peut modifier son profil, l'admin peut tout modifier
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request, Authentication authentication) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUserById(id);
        if (utilisateur.isPresent() && (authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ||
            authentication.getName().equals(utilisateur.get().getIdUser().toString()))) {


            return utilisateurService.updateUser(id, request.getEmail(), request.getPassword(), request.getName(), request.getPictureUrl())
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(403).build();
    }

    // 🔹 un user peut supprimer son compte, l'admin peut supprimer tout
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, Authentication authentication) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUserById(id);
        if (utilisateur.isPresent() && (authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ||
            authentication.getName().equals(utilisateur.get().getIdUser().toString()))) {


            return utilisateurService.deleteUser(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(403).build();
    }
}
