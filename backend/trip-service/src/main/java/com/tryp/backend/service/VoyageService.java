package com.tryp.backend.service;


import com.tryp.backend.repository.VoyageRepository;
import com.tryp.backend.model.Voyage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Service
public class VoyageService {

    private final VoyageRepository voyageRepository;

    public VoyageService(VoyageRepository voyageRepository){
        this.voyageRepository=voyageRepository;
    }

    public Voyage createVoyage(Long idUser, String nomVoyage, String descriptionVoyage,
        LocalDate dateDebutVoyage, LocalDate dateFinVoyage)
        {
            Voyage voyage = new Voyage();
            voyage.setIdUser(idUser);
            voyage.setNomVoyage(nomVoyage);
            voyage.setDescriptionVoyage(descriptionVoyage);
            voyage.setDateDebutVoyage(dateDebutVoyage);
            voyage.setDateFinVoyage(dateFinVoyage);
            voyage.setCreatedAt(LocalDateTime.now());

            return voyageRepository.save(voyage);
        }

         // 🔹 get all trips
     public List<Voyage> getAllVoyages() {
        return voyageRepository.findAll();
    }

    // 🔹 get one trip by id
    public Optional<Voyage> getVoyageById(Long id) {
        return voyageRepository.findById(id);
    }

    // 🔹 update user
    public Optional<Voyage> updateVoyage(Long id, String nomVoyage, String descriptionVoyage,
        LocalDate dateDebutVoyage, LocalDate dateFinVoyage)
        {
        return voyageRepository.findById(id).map(voyage -> {
            if (nomVoyage != null && !nomVoyage.isEmpty()) voyage.setNomVoyage(nomVoyage);
            if (descriptionVoyage != null && !descriptionVoyage.isEmpty()) voyage.setDescriptionVoyage(descriptionVoyage);
            if (dateDebutVoyage != null) voyage.setDateDebutVoyage(dateDebutVoyage);
            if (dateFinVoyage != null) voyage.setDateFinVoyage(dateFinVoyage);
            return voyageRepository.save(voyage);
        });
        }

    // 🔹 delete user
    public boolean deleteVoyage(Long id) {
        if (voyageRepository.existsById(id)) {
            voyageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

