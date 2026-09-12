
package com.skillmatch.project_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProjectRequest {

    private Long companyId;

    private String title;

    private String description;

    private BigDecimal budget;


    private List<String> requiredSkills;


    private String experienceLevel;
}