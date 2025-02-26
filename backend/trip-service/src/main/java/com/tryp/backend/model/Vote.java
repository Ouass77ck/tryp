package com.tryp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Vote", uniqueConstraints = @UniqueConstraint(columnNames = {"idUser", "idActivity"}))
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVote;

    @Column(nullable = false)
    private Long idUser;

    @Column(nullable = false)
    private Long idActivity;

    @Column(nullable = false)
    private int voteValue;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
