package com.skillmatch.user_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackDTO {
    private Long id;
    private Long contractId;
    private Long professionalId; // solo l'id, non l'oggetto User
    private Long raterId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}