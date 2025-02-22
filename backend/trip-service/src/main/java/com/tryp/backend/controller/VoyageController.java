package com.tryp.backend.controller;

import com.tryp.backend.model.Voyage;
import com.tryp.backend.service.VoyageService;
import com.tryp.backend.dto.VoyageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/voyages")
public class VoyageController {

    private final VoyageService voyageService;

    public VoyageController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    // 🔹 création de voyage
    // @PreAuthorize("hasRole('USER')")
    @PostMapping("/creation")
    public ResponseEntity<Voyage> createVoyage(@RequestBody VoyageRequest request) {
        Voyage Voyage = voyageService.createVoyage(
                request.getIdUser(),
                request.getNomVoyage(),
                request.getDescriptionVoyage(),
                request.getDateDebutVoyage(),
                request.getDateFinVoyage()
        );
        return ResponseEntity.ok(Voyage);
    }

    // 🔹 seuls les admins peuvent voir tous les voyages
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Voyage>> getAllVoyages() {
            return ResponseEntity.ok(voyageService.getAllVoyages());
    }

    // 🔹 accessible à tt les voyages
    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Voyage> getVoyageById(@PathVariable Long id) {
        Optional<Voyage> voyage = voyageService.getVoyageById(id);
        if (voyage.isPresent()) {
            return ResponseEntity.ok(voyage.get());
        }
        return ResponseEntity.status(403).build();
    }

    // 🔹 un user peut modifier son voyage, l'admin peut tout modifier
    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Voyage> updateVoyage(@PathVariable Long id, @RequestBody VoyageRequest request) {
        Optional<Voyage> voyage = voyageService.getVoyageById(id);
        if (voyage.isPresent())                 {
            return voyageService.updateVoyage(id,
                request.getNomVoyage(),
                request.getDescriptionVoyage(),
                request.getDateDebutVoyage(),
                request.getDateFinVoyage())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
                }

        return ResponseEntity.status(403).build();
    }

    // 🔹 un user peut supprimer un de ses voyages, l'admin peut supprimer tout
    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoyage(@PathVariable Long id) {
        Optional<Voyage> voyage = voyageService.getVoyageById(id);
        if (voyage.isPresent()) {
            return voyageService.deleteVoyage(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(403).build();
    }
}
