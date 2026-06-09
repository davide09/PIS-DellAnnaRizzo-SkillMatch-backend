package com.skillmatch.user_service.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String portfolioUrl;
    private String certifications;
    private String notes;
}