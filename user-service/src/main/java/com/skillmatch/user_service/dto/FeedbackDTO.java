package com.skillmatch.user_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackDTO {
    private Long id;
    private Long contractId;
    private Long professionalId;
    private Long raterId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}