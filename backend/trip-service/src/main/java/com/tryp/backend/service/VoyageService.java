package com.tryp.backend.service;

import com.tryp.backend.dto.VoyageRequest;
import com.tryp.backend.model.Voyage;
import com.tryp.backend.repository.VoyageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoyageService {

    private final VoyageRepository voyageRepository;

    // 🔹 Créer un voyage
    public Voyage createVoyage(VoyageRequest request) {
        Voyage voyage = new Voyage();
        voyage.setIdUser(request.getIdUser());
        voyage.setNomVoyage(request.getNomVoyage());
        voyage.setDescriptionVoyage(request.getDescriptionVoyage());
        voyage.setDateDebutVoyage(request.getDateDebutVoyage());
        voyage.setDateFinVoyage(request.getDateFinVoyage());
        voyage.setCreatedAt(LocalDateTime.now());

        return voyageRepository.save(voyage);
    }

    // 🔹 Obtenir tous les voyages
    public List<Voyage> getAllVoyages() {
        return voyageRepository.findAll();
    }

    // 🔹 Obtenir un voyage par ID
    public Optional<Voyage> getVoyageById(Long id) {
        return voyageRepository.findById(id);
    }

    // 🔹 Mettre à jour un voyage
    public Optional<Voyage> updateVoyage(Long id, VoyageRequest request) {
        return voyageRepository.findById(id).map(voyage -> {
            if (request.getNomVoyage() != null) voyage.setNomVoyage(request.getNomVoyage());
            if (request.getDescriptionVoyage() != null) voyage.setDescriptionVoyage(request.getDescriptionVoyage());
            if (request.getDateDebutVoyage() != null) voyage.setDateDebutVoyage(request.getDateDebutVoyage());
            if (request.getDateFinVoyage() != null) voyage.setDateFinVoyage(request.getDateFinVoyage());
            return voyageRepository.save(voyage);
        });
    }

    // 🔹 Supprimer un voyage
    public boolean deleteVoyage(Long id) {
        if (voyageRepository.existsById(id)) {
            voyageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean isAdminOfTheTravel(Long idUser, Long idVoyage) {
    Optional<Voyage> voyageOpt = voyageRepository.findById(idVoyage);
    
    if (voyageOpt.isPresent()) {
        return voyageOpt.get().getIdUser().equals(idUser);
    }
    
    return false;
    }

}
