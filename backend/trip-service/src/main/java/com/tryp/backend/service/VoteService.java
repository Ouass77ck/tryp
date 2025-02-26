package com.tryp.backend.service;

import com.tryp.backend.dto.VoteRequest;
import com.tryp.backend.dto.VoteResponse;
import com.tryp.backend.model.Vote;
import com.tryp.backend.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteService {
    
    @Autowired
    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public VoteResponse createVote(VoteRequest request) {
        Optional<Vote> existingVote = voteRepository.findByIdUserAndIdActivity(request.getIdUser(), request.getIdActivity());
        if (existingVote.isPresent()) {
            throw new RuntimeException("L'utilisateur a déjà voté pour cette activité.");
        }
        Vote vote = new Vote();
        vote.setIdUser(request.getIdUser());
        vote.setIdActivity(request.getIdActivity());
        vote.setVoteValue(request.getVoteValue());

        Vote savedVote = voteRepository.save(vote);
        return mapToResponse(savedVote);
    }

    @Transactional
    public void deleteVote(Long idUser, Long idActivity) {
        voteRepository.deleteByIdUserAndIdActivity(idUser, idActivity);
    }

    public VoteResponse getVote(Long idUser, Long idActivity) {
        Vote vote = voteRepository.findByIdUserAndIdActivity(idUser, idActivity)
                .orElseThrow(() -> new RuntimeException("Aucun vote trouvé."));
        return mapToResponse(vote);
    }

    private VoteResponse mapToResponse(Vote vote) {
        VoteResponse response = new VoteResponse();
        response.setIdVote(vote.getIdVote());
        response.setIdUser(vote.getIdUser());
        response.setIdActivity(vote.getIdActivity());
        response.setVoteValue(vote.getVoteValue());
        return response;
    }
}
