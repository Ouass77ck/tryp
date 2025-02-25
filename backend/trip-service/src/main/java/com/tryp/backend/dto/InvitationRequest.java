package com.tryp.backend.dto;

import lombok.Data;

@Data
public class InvitationRequest {
    private Long idUser;        // Utilisateur qui invite
    private Long idUserInvite;
    private Long idVoyage;   
}
