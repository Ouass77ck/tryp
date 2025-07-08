package com.tryp.backend.controller;

import com.tryp.backend.dto.VoteRequest;
import com.tryp.backend.dto.VoteResponse;
import com.tryp.backend.model.Activity;
import com.tryp.backend.model.Voyage;
import com.tryp.backend.service.VoteService;
import com.tryp.backend.service.ActivityService;
import com.tryp.backend.service.InvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;
    private final ActivityService activityService;
    private final InvitationService invitationService;

    public VoteController(VoteService voteService, ActivityService activityService, InvitationService invitationService)  {
        this.voteService = voteService;
        this.activityService = activityService;
        this.invitationService = invitationService;
    }

    @PostMapping
    public ResponseEntity<VoteResponse> createVote(@RequestBody VoteRequest request, Authentication authentication) {
        Optional<Activity> existingActivity= activityService.getActivityById(request.getIdActivity());

         if (existingActivity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Activity activity = existingActivity.get(); //on remonte à l'activité pour pouvoir remonter aux voyages et obtenir l'id
        Voyage voyage = activity.getVoyage();//et ainsi les participants aux voyages histoire de savoir si on a le droit 

        Long currentUserId = Long.valueOf(authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerOfTheTravel = currentUserId.equals(voyage.getIdUser());
        boolean isInvitedOnTheTravel =  invitationService.isUserAcceptedInVoyage(currentUserId, voyage.getIdVoyage());

        if (isAdmin || isOwnerOfTheTravel || isInvitedOnTheTravel){
            VoteResponse response = voteService.createVote(request);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(403).build();
    }
    @GetMapping
    public ResponseEntity<VoteResponse> getVote(@RequestParam Long idUser, @RequestParam Long idActivity, Authentication authentication) {
        Optional<Activity> existingActivity= activityService.getActivityById(idActivity);

         if (existingActivity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Activity activity = existingActivity.get(); 
        Voyage voyage = activity.getVoyage();

        Long currentUserId = Long.valueOf(authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerOfTheTravel = currentUserId.equals(voyage.getIdUser());
        boolean isInvitedOnTheTravel =  invitationService.isUserAcceptedInVoyage(currentUserId, voyage.getIdVoyage());
        
        if (isAdmin || isOwnerOfTheTravel || isInvitedOnTheTravel){
            VoteResponse response = voteService.getVote(idUser, idActivity);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(403).build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteVote(@RequestParam Long idUser, @RequestParam Long idActivity, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isUser = authentication.getName().equals(idUser.toString());
        if (isAdmin || isUser){
            voteService.deleteVote(idUser, idActivity);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(403).build();
    }       

    @GetMapping("/activity/{idActivity}")
    public ResponseEntity<List<VoteResponse>> getVotesByActivity(@PathVariable Long idActivity, Authentication authentication) {
        Optional<Activity> existingActivity= activityService.getActivityById(idActivity);

         if (existingActivity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Activity activity = existingActivity.get(); 
        Voyage voyage = activity.getVoyage();

        Long currentUserId = Long.valueOf(authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerOfTheTravel = currentUserId.equals(voyage.getIdUser());
        boolean isInvitedOnTheTravel =  invitationService.isUserAcceptedInVoyage(currentUserId, voyage.getIdVoyage());
        
        if (isAdmin || isOwnerOfTheTravel || isInvitedOnTheTravel){
            List<VoteResponse> votes = voteService.getVotesByActivity(idActivity);
            return ResponseEntity.ok(votes);
        }
        return ResponseEntity.status(403).build();
    }   

    @GetMapping("/activity/{idActivity}/score")
    public ResponseEntity<Integer> getScoreByActivity(@PathVariable Long idActivity, Authentication authentication) {
        Optional<Activity> existingActivity= activityService.getActivityById(idActivity);

         if (existingActivity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Activity activity = existingActivity.get(); 
        Voyage voyage = activity.getVoyage();

        Long currentUserId = Long.valueOf(authentication.getName());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerOfTheTravel = currentUserId.equals(voyage.getIdUser());
        boolean isInvitedOnTheTravel =  invitationService.isUserAcceptedInVoyage(currentUserId, voyage.getIdVoyage());
        
        if (isAdmin || isOwnerOfTheTravel || isInvitedOnTheTravel){
            Integer score = voteService.getScoreByActivity(idActivity);
            return ResponseEntity.ok(score);
        }
        return ResponseEntity.status(403).build();
    }
}
