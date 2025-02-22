package com.tryp.backend.repository;

import com.tryp.backend.model.Inviter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface InviterRepository extends JpaRepository<Inviter, Long> {
    Optional<Inviter> findById(Long idInvitation);
    boolean existsById(Long idInvitation);
}
