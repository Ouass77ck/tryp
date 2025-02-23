package com.tryp.backend.controller;

import com.tryp.backend.dto.ActivityResponse;
import com.tryp.backend.dto.ActivityRequest;
import com.tryp.backend.model.Activity;
import com.tryp.backend.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    // 🔹 Créer une activité
    @PostMapping("/creation")
    public ResponseEntity<ActivityResponse> createActivity(@RequestBody ActivityRequest createActivityResponse) {
        Activity createdActivity = activityService.createActivity(createActivityResponse);
        return ResponseEntity.ok(convertToDTO(createdActivity));
    }

    // 🔹 Récupérer toutes les activités
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities() {
        List<ActivityResponse> activities = activityService.getAllActivities()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activities);
    }

    // 🔹 Récupérer une activité par ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable Long id) {
        Optional<Activity> activity = activityService.getActivityById(id);
        return activity.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Mettre à jour une activité
    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable Long id, @RequestBody ActivityResponse activityDTO) {
        Optional<Activity> updatedActivity = activityService.updateActivity(id, activityDTO);
        return updatedActivity.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Supprimer une activité
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        boolean deleted = activityService.deleteActivity(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
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
