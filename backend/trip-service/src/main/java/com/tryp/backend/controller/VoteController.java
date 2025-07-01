package com.tryp.backend.controller;

import com.tryp.backend.dto.VoteRequest;
import com.tryp.backend.dto.VoteResponse;
import com.tryp.backend.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<VoteResponse> createVote(@RequestBody VoteRequest request, Authentication authentication) {
        VoteResponse response = voteService.createVote(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<VoteResponse> getVote(@RequestParam Long idUser, @RequestParam Long idActivity, Authentication authentication) {
        VoteResponse response = voteService.getVote(idUser, idActivity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVote(@RequestParam Long idUser, @RequestParam Long idActivity, Authentication authentication) {
        voteService.deleteVote(idUser, idActivity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activity/{idActivity}")
    public ResponseEntity<List<VoteResponse>> getVotesByActivity(@PathVariable Long idActivity, Authentication authentication) {
        List<VoteResponse> votes = voteService.getVotesByActivity(idActivity);
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/activity/{idActivity}/score")
    public ResponseEntity<Integer> getScoreByActivity(@PathVariable Long idActivity, Authentication authentication) {
        Integer score = voteService.getScoreByActivity(idActivity);
        return ResponseEntity.ok(score);
    }
}
