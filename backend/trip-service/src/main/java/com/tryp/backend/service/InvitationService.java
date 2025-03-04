package com.tryp.backend.service;

import com.tryp.backend.dto.InvitationRequest;
import com.tryp.backend.dto.InvitationResponse;
import com.tryp.backend.model.Inviter;
import com.tryp.backend.model.Voyage;
import com.tryp.backend.repository.InviterRepository;
import com.tryp.backend.repository.VoyageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvitationService {

    private final InviterRepository inviterRepository;
    private final VoyageRepository voyageRepository;

    public InvitationService(InviterRepository inviterRepository, VoyageRepository voyageRepository) {
        this.inviterRepository = inviterRepository;
        this.voyageRepository = voyageRepository;
    }

    public InvitationResponse createInvitation(InvitationRequest request) {
        Voyage voyage = voyageRepository.findById(request.getIdVoyage())
                .orElseThrow(() -> new RuntimeException("Voyage introuvable"));

        Inviter invitation = new Inviter();
        invitation.setIdUser(request.getIdUser());
        invitation.setIdUserInvite(request.getIdUserInvite());
        invitation.setVoyage(voyage);
        invitation.setStatus("pending");

        Inviter savedInvitation = inviterRepository.save(invitation);
        return mapToResponse(savedInvitation);
    }

    public List<InvitationResponse> getInvitationsByUser(Long userId) {
        List<Inviter> invitations = inviterRepository.findByIdUserInvite(userId);
        return invitations.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public InvitationResponse updateInvitationStatus(Long invitationId, String newStatus) {
        Inviter invitation = inviterRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation introuvable"));

        if ("accepted".equals(newStatus)) {
            invitation.setStatus("accepted");
            Inviter updatedInvitation = inviterRepository.save(invitation);
            return mapToResponse(updatedInvitation);
        } else if ("declined".equals(newStatus)) {
            inviterRepository.delete(invitation);
            return null;
        } else {
            throw new IllegalArgumentException("Statut invalide");
        }
    }


    private InvitationResponse mapToResponse(Inviter invitation) {
        InvitationResponse response = new InvitationResponse();
        response.setIdInvitation(invitation.getIdInvitation());
        response.setIdUser(invitation.getIdUser());
        response.setIdUserInvite(invitation.getIdUserInvite());
        response.setIdVoyage(invitation.getVoyage().getIdVoyage());
        response.setStatus(invitation.getStatus());
        response.setCreatedAt(invitation.getCreatedAt());
        return response;
    }

    public InvitationResponse getInvitationById(Long invitationId) {
        Inviter invitation = inviterRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation introuvable"));
        return mapToResponse(invitation);
    }

    public void deleteInvitationById(Long invitationId) {
        if (!inviterRepository.existsById(invitationId)) {
            throw new RuntimeException("Invitation introuvable");
        }
        inviterRepository.deleteById(invitationId);
    }

    public List<InvitationResponse> getAcceptedInvitationsByVoyage(Long idVoyage) {
    List<Inviter> acceptedInvitations = inviterRepository.findByVoyage_IdVoyageAndStatus(idVoyage, "accepted");
    return acceptedInvitations.stream().map(this::mapToResponse).collect(Collectors.toList());
}

}
