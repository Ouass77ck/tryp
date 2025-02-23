package com.tryp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "Voyage")
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoyage;

    private Long idUser;

    @Column(nullable = false)
    private String nomVoyage;

    private String descriptionVoyage;

    @Column(nullable = false)
    private LocalDate dateDebutVoyage;

    @Column(nullable = false)
    private LocalDate dateFinVoyage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "voyage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities= new ArrayList<>();  // Liste des activités associées
}
