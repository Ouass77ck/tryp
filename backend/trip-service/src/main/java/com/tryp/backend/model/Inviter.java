package com.tryp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Inviter")
public class Inviter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvitation;

    @Column(nullable = false)
    private Long idUser; // L'utilisateur qui invite

    @Column(nullable = false)
    private Long idUserInvite; // L'utilisateur invité

    @ManyToOne
    @JoinColumn(name = "id_voyage", nullable = false)
    private Voyage voyage;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
