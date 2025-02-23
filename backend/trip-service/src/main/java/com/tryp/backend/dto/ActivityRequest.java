package com.tryp.backend.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ActivityRequest {
    private Long idVoyage;
    private String nomActivity;
    private String lieuActivity;
    private LocalDate dateActivity;
    private LocalTime timeActivity;
    private String descriptionActivity;
}
