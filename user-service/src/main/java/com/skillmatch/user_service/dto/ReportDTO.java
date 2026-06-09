package com.skillmatch.user_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDTO {
    private Long id;
    private Long reporterId;
    private Long reportedUserId;
    private String description;
    private String status;
    private LocalDateTime createdAt;
}