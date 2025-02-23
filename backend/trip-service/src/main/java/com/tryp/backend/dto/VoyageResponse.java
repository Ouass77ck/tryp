package com.tryp.backend.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VoyageResponse {
    private Long idVoyage;
    private Long idUser;
    private String nomVoyage;
    private String descriptionVoyage;
    private LocalDate dateDebutVoyage;
    private LocalDate dateFinVoyage;
    private List<ActivityRequest> activities;  // Liste des activités associées
}
