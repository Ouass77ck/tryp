package com.tryp.backend.repository;

import com.tryp.backend.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findById(Long idActivity);
    boolean existsById(Long idActivity);
}
