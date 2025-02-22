package com.tryp.backend.repository;

import com.tryp.backend.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    Optional<Voyage> findById(Long idVoyage);
    boolean existsById(Long idVoyage);
}
