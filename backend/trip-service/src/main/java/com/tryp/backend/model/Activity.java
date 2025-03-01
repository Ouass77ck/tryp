package com.tryp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_voyage", nullable = false)  // Définit la clé étrangère
    private Voyage voyage;  // Objet directement lié

    @Column(nullable = false)
    private String nomActivity;

    private String lieuActivity;

    @Column(nullable = false)
    private LocalDate dateActivity;

    private LocalTime timeActivity;

    private String descriptionActivity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
