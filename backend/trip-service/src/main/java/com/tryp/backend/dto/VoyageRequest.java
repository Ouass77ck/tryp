package com.tryp.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VoyageRequest {
    private Long idUser;
    private String nomVoyage;
    private String descriptionVoyage;
    private LocalDate dateDebutVoyage;
    private LocalDate dateFinVoyage;
}
