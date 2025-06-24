package com.tryp.backend.controller;

import com.tryp.backend.dto.InvitationRequest;
import com.tryp.backend.dto.InvitationResponse;
import com.tryp.backend.service.InvitationService;
import com.tryp.backend.service.VoyageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.lang.Long;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;
    private final VoyageService voyageService;

    public InvitationController(InvitationService invitationService, VoyageService voyageService) {
        this.invitationService = invitationService;
        this.voyageService = voyageService;
    }

    @PostMapping
    public ResponseEntity<InvitationResponse> createInvitation(@RequestBody InvitationRequest request, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = authentication.getName().equals(request.getIdUser().toString());
        boolean isAdminOfTheTravel = voyageService.isAdminOfTheTravel(request.getIdUser(), request.getIdVoyage());

        if (isAdmin || (isOwner && isAdminOfTheTravel)){
            InvitationResponse response = invitationService.createInvitation(request);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvitationResponse>> getInvitationsByUser(@PathVariable Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isDestinatary = authentication.getName().equals(userId.toString());

        if (isAdmin || isDestinatary)   {  
            List<InvitationResponse> invitations = invitationService.getInvitationsByUser(userId);
            return ResponseEntity.ok(invitations);
        }
        return ResponseEntity.status(403).build();
    }

    @PutMapping("/{invitationId}")
    public ResponseEntity<InvitationResponse> updateInvitationStatus(@PathVariable Long invitationId, 
                                                                    @RequestParam String status, Authentication authentication) {
        InvitationResponse invit = invitationService.getInvitationById(invitationId);                                                                
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isDestinatary = authentication.getName().equals(invit.getIdUserInvite());

        if (isAdmin || isDestinatary)   {                                             
            InvitationResponse response = invitationService.updateInvitationStatus(invitationId, status);
            
            if (response == null) {
                return ResponseEntity.noContent().build(); // 204 No Content si supprimé
            }

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(403).build();       
}

    @GetMapping("/{invitationId}")
    public ResponseEntity<InvitationResponse> getInvitationById(@PathVariable Long invitationId, Authentication authentication) {
        InvitationResponse response = invitationService.getInvitationById(invitationId);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isDestinatary = authentication.getName().equals(response.getIdUserInvite());

        if (isAdmin||isDestinatary) {   
            return ResponseEntity.ok(response);
            }
        return ResponseEntity.status(403).build();
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<Void> deleteInvitationById(@PathVariable Long invitationId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            invitationService.deleteInvitationById(invitationId);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(403).build();
}

    // here we need a function that takes one trip and responds with everyone who accepted the invitation
   @GetMapping("/voyage/{idVoyage}")
    public ResponseEntity<List<InvitationResponse>> getInvitationsByTrip(@PathVariable Long idVoyage, Authentication authentication) {
         List<InvitationResponse> response = invitationService.getAcceptedInvitationsByVoyage(idVoyage);
        //TODO
        //mettre un bout de code qui permets de vérifier que la personne qui demande fait partie du voyage

        //if (isAdmin||isDestinatary) {   
        return ResponseEntity.ok(response);
            //}
        //return ResponseEntity.status(403).build();
    }
 
}
