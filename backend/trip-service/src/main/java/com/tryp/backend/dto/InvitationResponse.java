package com.tryp.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InvitationResponse {
    private Long idInvitation;
    private Long idUser;
    private Long idUserInvite;
    private Long idVoyage;
    private String status;
    private LocalDateTime createdAt;
}
