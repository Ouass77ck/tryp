package com.tryp.backend.service;

import com.tryp.backend.dto.ActivityResponse;
import com.tryp.backend.dto.ActivityRequest;
import com.tryp.backend.model.Activity;
import com.tryp.backend.model.Voyage;
import com.tryp.backend.repository.ActivityRepository;
import com.tryp.backend.repository.VoyageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final VoyageRepository voyageRepository;

    // 🔹 Créer une activité (en utilisant ActivityRequest)
    public Activity createActivity(ActivityRequest createActivityResponse) {
        Voyage voyage = voyageRepository.findById(createActivityResponse.getIdVoyage())
                .orElseThrow(() -> new IllegalArgumentException("Voyage non trouvé"));

        Activity activity = new Activity();
        activity.setVoyage(voyage);
        activity.setNomActivity(createActivityResponse.getNomActivity());
        activity.setLieuActivity(createActivityResponse.getLieuActivity());
        activity.setDateActivity(createActivityResponse.getDateActivity());
        activity.setTimeActivity(createActivityResponse.getTimeActivity());
        activity.setDescriptionActivity(createActivityResponse.getDescriptionActivity());

        return activityRepository.save(activity);
    }

    // 🔹 Obtenir toutes les activités
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // 🔹 Obtenir une activité par ID
    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    // 🔹 Mettre à jour une activité
    public Optional<Activity> updateActivity(Long id, ActivityResponse activityDTO) {
        return activityRepository.findById(id).map(activity -> {
            if (activityDTO.getNomActivity() != null) activity.setNomActivity(activityDTO.getNomActivity());
            if (activityDTO.getLieuActivity() != null) activity.setLieuActivity(activityDTO.getLieuActivity());
            if (activityDTO.getDateActivity() != null) activity.setDateActivity(activityDTO.getDateActivity());
            if (activityDTO.getTimeActivity() != null) activity.setTimeActivity(activityDTO.getTimeActivity());
            if (activityDTO.getDescriptionActivity() != null) activity.setDescriptionActivity(activityDTO.getDescriptionActivity());
            return activityRepository.save(activity);
        });
    }

    // 🔹 Supprimer une activité
    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
