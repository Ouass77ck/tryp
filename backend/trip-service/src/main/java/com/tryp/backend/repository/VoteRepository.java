package com.tryp.backend.repository;

import com.tryp.backend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByIdUserAndIdActivity(Long idUser, Long idActivity);
    @Modifying
    @Transactional
    void deleteByIdUserAndIdActivity(Long idUser, Long idActivity);
}
