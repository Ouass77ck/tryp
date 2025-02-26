package com.tryp.backend.dto;

import lombok.Data;

@Data
public class VoteResponse {
    private Long idVote;
    private Long idUser;
    private Long idActivity;
    private int voteValue;
}
