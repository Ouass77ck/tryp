package com.tryp.backend.controller;

import com.tryp.backend.dto.VoyageRequest;
import com.tryp.backend.dto.VoyageResponse;
import com.tryp.backend.dto.ActivityRequest;
import com.tryp.backend.model.Voyage;
import com.tryp.backend.service.VoyageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/voyages")
@RequiredArgsConstructor
public class VoyageController {

    private final VoyageService voyageService;

    // 🔹 Créer un voyage
    @PostMapping("/creation")
    public ResponseEntity<VoyageResponse> createVoyage(@RequestBody VoyageRequest request) {
        Voyage voyage = voyageService.createVoyage(request);
        return ResponseEntity.ok(convertToResponse(voyage));
    }

    // 🔹 Obtenir tous les voyages
    @GetMapping
    public ResponseEntity<List<VoyageResponse>> getAllVoyages() {
        List<VoyageResponse> voyages = voyageService.getAllVoyages()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(voyages);
    }

    // 🔹 Obtenir un voyage par ID
    @GetMapping("/{id}/get")
    public ResponseEntity<VoyageResponse> getVoyageById(@PathVariable Long id, Authentication authentication) {
        Optional<Voyage> voyage = voyageService.getVoyageById(id);
            return voyage.map(value -> ResponseEntity.ok(convertToResponse(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Mettre à jour un voyage
    @PutMapping("/{id}/put")
    public ResponseEntity<VoyageResponse> updateVoyage(@PathVariable Long id, 
                                                    @RequestBody VoyageRequest request, 
                                                    Authentication authentication) {
        Optional<Voyage> existingVoyage = voyageService.getVoyageById(id);
        if (existingVoyage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = authentication.getName().equals(existingVoyage.get().getIdUser().toString());
        
        if (isAdmin || isOwner) {
            Optional<Voyage> updatedVoyage = voyageService.updateVoyage(id, request);
            return updatedVoyage
                    .map(value -> ResponseEntity.ok(convertToResponse(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        
        return ResponseEntity.status(403).build();
    }


    // 🔹 Supprimer un voyage
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteVoyage(@PathVariable Long id, Authentication authentication) {
        Optional<Voyage> existingVoyage = voyageService.getVoyageById(id);
        if (existingVoyage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = authentication.getName().equals(existingVoyage.get().getIdUser().toString());
        
        if (isAdmin || isOwner) {
            boolean deleted = voyageService.deleteVoyage(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.status(403).build();
    }


    // 🔹 Convertir un `Voyage` en `VoyageResponse`
    private VoyageResponse convertToResponse(Voyage voyage) {
        VoyageResponse response = new VoyageResponse();
        response.setIdVoyage(voyage.getIdVoyage());
        response.setIdUser(voyage.getIdUser());
        response.setNomVoyage(voyage.getNomVoyage());
        response.setDescriptionVoyage(voyage.getDescriptionVoyage());   
        response.setDateDebutVoyage(voyage.getDateDebutVoyage());
        response.setDateFinVoyage(voyage.getDateFinVoyage());

        // Convertir la liste des activités associées
        response.setActivities(voyage.getActivities().stream().map(activity -> {
            ActivityRequest dto = new ActivityRequest();
            dto.setIdVoyage(voyage.getIdVoyage());
            dto.setNomActivity(activity.getNomActivity());
            dto.setLieuActivity(activity.getLieuActivity());
            dto.setDateActivity(activity.getDateActivity());
            dto.setTimeActivity(activity.getTimeActivity());
            dto.setDescriptionActivity(activity.getDescriptionActivity());
            return dto;
        }).collect(Collectors.toList()));

        return response;
    }
}
