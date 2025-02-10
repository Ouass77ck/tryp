package com.tryp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "Inviter")
public class Inviter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvitation;

    private Long idUser;
    private Long idUserInvite;
    private Long idVoyage;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'pending'")
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}