package com.tryp.backend.dto;

import lombok.Data;

@Data
public class VoteRequest {
    private Long idUser;
    private Long idActivity;
    private int voteValue; // 1 ou -1
}
