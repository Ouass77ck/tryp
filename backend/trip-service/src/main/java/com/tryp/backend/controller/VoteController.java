package com.tryp.backend.controller;

import com.tryp.backend.dto.VoteRequest;
import com.tryp.backend.dto.VoteResponse;
import com.tryp.backend.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<VoteResponse> createVote(@RequestBody VoteRequest request) {
        VoteResponse response = voteService.createVote(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<VoteResponse> getVote(@RequestParam Long idUser, @RequestParam Long idActivity) {
        VoteResponse response = voteService.getVote(idUser, idActivity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVote(@RequestParam Long idUser, @RequestParam Long idActivity) {
        voteService.deleteVote(idUser, idActivity);
        return ResponseEntity.noContent().build();
    }
}
