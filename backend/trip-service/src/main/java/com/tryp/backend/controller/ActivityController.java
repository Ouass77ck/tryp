package com.tryp.backend.controller;

import com.tryp.backend.dto.ActivityResponse;
import com.tryp.backend.dto.ActivityRequest;
import com.tryp.backend.model.Activity;
import com.tryp.backend.model.Voyage;
import com.tryp.backend.service.ActivityService;
import com.tryp.backend.service.VoyageService;
import com.tryp.backend.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final VoyageService voyageService;
    private final InvitationService invitationService;

    // 🔹 Créer une activité
    @PostMapping("/creation")
    public ResponseEntity<ActivityResponse> createActivity(@RequestBody ActivityRequest createActivityResponse, Authentication authentication) {
        Optional<Voyage> existingVoyage = voyageService.getVoyageById(createActivityResponse.getIdVoyage());

        if (existingVoyage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Voyage voyage = existingVoyage.get();
        Long currentUserId = Long.valueOf(authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerOfTheTravel = currentUserId.equals(voyage.getIdUser());
        boolean isInvitedOnTheTravel =  invitationService.isUserAcceptedInVoyage(currentUserId, voyage.getIdVoyage());

        if (isAdmin || isOwnerOfTheTravel || isInvitedOnTheTravel){
            Activity createdActivity = activityService.createActivity(createActivityResponse);
            return ResponseEntity.ok(convertToDTO(createdActivity));
    }
        return ResponseEntity.status(403).build();            
}

    // 🔹 Récupérer toutes les activités
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin){
            List<ActivityResponse> activities = activityService.getAllActivities()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(activities);
        }
        return ResponseEntity.status(403).build();    
    }

    // 🔹 Récupérer une activité par ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable Long id, Authentication authentication) {
        
        Optional<Activity> existingActivity = activityService.getActivityById(id);

        if (existingActivity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Activity activity = existingActivity.get();

        Voyage voyage = activity.getVoyage();


        Long currentUserId = Long.valueOf(authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerOfTheTravel = currentUserId.equals(voyage.getIdUser());
        boolean isInvitedOnTheTravel =  invitationService.isUserAcceptedInVoyage(currentUserId, voyage.getIdVoyage());

        if (isAdmin || isOwnerOfTheTravel || isInvitedOnTheTravel){
            return ResponseEntity.ok(convertToDTO(activity));
        }
        return ResponseEntity.status(403).build();    
    }

    // 🔹 Mettre à jour une activité
    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable Long id, @RequestBody ActivityResponse activityDTO, Authentication authentication) {
        Optional<Activity> existingActivity = activityService.getActivityById(id);

        if (existingActivity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Activity activity = existingActivity.get();

        Voyage voyage = activity.getVoyage();


        Long currentUserId = Long.valueOf(authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerOfTheTravel = currentUserId.equals(voyage.getIdUser());

        if (isAdmin || isOwnerOfTheTravel){
        Optional<Activity> updatedActivity = activityService.updateActivity(id, activityDTO);
        return updatedActivity.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(403).build();    
    }

    // 🔹 Supprimer une activité
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id, Authentication authentication) {
        Optional<Activity> existingActivity = activityService.getActivityById(id);

        if (existingActivity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Activity activity = existingActivity.get();

        Voyage voyage = activity.getVoyage();


        Long currentUserId = Long.valueOf(authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerOfTheTravel = currentUserId.equals(voyage.getIdUser());

        if (isAdmin || isOwnerOfTheTravel){
            boolean deleted = activityService.deleteActivity(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
        return ResponseEntity.status(403).build();    
     }

    // 🔹 Convertir une entité `Activity` en DTO
    private ActivityResponse convertToDTO(Activity activity) {
        ActivityResponse dto = new ActivityResponse();
        dto.setIdActivity(activity.getIdActivity());
        dto.setIdVoyage(activity.getVoyage().getIdVoyage());
        dto.setNomActivity(activity.getNomActivity());
        dto.setLieuActivity(activity.getLieuActivity());
        dto.setDateActivity(activity.getDateActivity());
        dto.setTimeActivity(activity.getTimeActivity());
        dto.setDescriptionActivity(activity.getDescriptionActivity());
        return dto;
    }
}
