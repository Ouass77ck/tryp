package com.tryp.backend.service;

import com.tryp.backend.model.Utilisateur;
import com.tryp.backend.model.Role;
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
        Role role = Role.USER;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setPasswordHash(passwordEncoder.encode(password));
        utilisateur.setName(name);
        utilisateur.setPictureUrl(pictureUrl);
        utilisateur.setCreatedAt(LocalDateTime.now());
        utilisateur.setRole(role);

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

    // 🔹 update user
    public Optional<Utilisateur> updateUser(Long id, String email, String password, String name, String pictureUrl) {
        return utilisateurRepository.findById(id).map(utilisateur -> {
            if (email != null && !email.isEmpty()) utilisateur.setEmail(email);
            if (password != null && !password.isEmpty()) utilisateur.setPasswordHash(passwordEncoder.encode(password));
            if (name != null && !name.isEmpty()) utilisateur.setName(name);
            if (pictureUrl != null && !pictureUrl.isEmpty()) utilisateur.setPictureUrl(pictureUrl);
            return utilisateurRepository.save(utilisateur);
        });
    }

    // 🔹 delete user
    public boolean deleteUser(Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
