package com.tryp.backend.repository;

import com.tryp.backend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByIdActivity(Long idActivity);

    Optional<Vote> findByIdUserAndIdActivity(Long idUser, Long idActivity);

    @Modifying
    @Transactional
    void deleteByIdUserAndIdActivity(Long idUser, Long idActivity);

    @Query("SELECT SUM(v.voteValue) FROM Vote v WHERE v.idActivity = :idActivity")
    Integer getScoreByActivity(Long idActivity);
}
