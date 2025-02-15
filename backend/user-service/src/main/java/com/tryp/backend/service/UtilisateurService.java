package com.tryp.backend.service;

import com.tryp.backend.model.Utilisateur;
import com.tryp.backend.repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, BCryptPasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 register one user
    public Utilisateur createUser(String email, String password, String name, String pictureUrl) {
        if (utilisateurRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setPasswordHash(passwordEncoder.encode(password));
        utilisateur.setName(name);
        utilisateur.setPictureUrl(pictureUrl);
        utilisateur.setCreatedAt(LocalDateTime.now());

        return utilisateurRepository.save(utilisateur);
    }

     // 🔹 get all users
     public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    // 🔹 get one user by id
    public Optional<Utilisateur> getUserById(Long id) {
        return utilisateurRepository.findById(id);
    }
}
