package com.tryp.backend.repository;

import com.tryp.backend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findById(Long idVote);
    boolean existsById(Long idVote);
}
