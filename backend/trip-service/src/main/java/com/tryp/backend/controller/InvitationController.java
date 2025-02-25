package com.tryp.backend.controller;

import com.tryp.backend.dto.InvitationRequest;
import com.tryp.backend.dto.InvitationResponse;
import com.tryp.backend.service.InvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public ResponseEntity<InvitationResponse> createInvitation(@RequestBody InvitationRequest request) {
        InvitationResponse response = invitationService.createInvitation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvitationResponse>> getInvitationsByUser(@PathVariable Long userId) {
        List<InvitationResponse> invitations = invitationService.getInvitationsByUser(userId);
        return ResponseEntity.ok(invitations);
    }

    @PutMapping("/{invitationId}")
    public ResponseEntity<InvitationResponse> updateInvitationStatus(@PathVariable Long invitationId, 
                                                                    @RequestParam String status) {
                                                                        
        InvitationResponse response = invitationService.updateInvitationStatus(invitationId, status);
        
        if (response == null) {
            return ResponseEntity.noContent().build(); // 204 No Content si supprimé
        }

        return ResponseEntity.ok(response);
}

    @GetMapping("/{invitationId}")
    public ResponseEntity<InvitationResponse> getInvitationById(@PathVariable Long invitationId) {
        InvitationResponse response = invitationService.getInvitationById(invitationId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<Void> deleteInvitationById(@PathVariable Long invitationId) {
        invitationService.deleteInvitationById(invitationId);
        return ResponseEntity.noContent().build(); // 204 No Content
}}
