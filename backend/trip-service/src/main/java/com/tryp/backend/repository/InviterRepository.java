package com.tryp.backend.repository;

import com.tryp.backend.model.Inviter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface InviterRepository extends JpaRepository<Inviter, Long> {
    List<Inviter> findByVoyage_IdVoyageAndStatus(Long idVoyage, String status);
    Optional<Inviter> findById(Long idInvitation);
    List<Inviter> findByIdUserInvite(Long idUserInvite);
    boolean existsById(Long idInvitation);
}
