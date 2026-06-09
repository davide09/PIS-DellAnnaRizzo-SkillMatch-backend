
package com.skillmatch.matching_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    private Long companyId;
    private String title;
    private String description;
    private List<String> requiredSkills;
    private String experienceLevel;
    private String status;
}