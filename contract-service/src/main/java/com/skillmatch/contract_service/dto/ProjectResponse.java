
package com.skillmatch.contract_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectResponse {

    private Long id;
    private Long companyId;

    private String title;
    private String description;

    private BigDecimal budget;

    private List<String> requiredSkills;

    private String experienceLevel;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}