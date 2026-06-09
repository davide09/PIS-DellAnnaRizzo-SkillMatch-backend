
package com.skillmatch.project_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillmatch.project_service.model.ProjectStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
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