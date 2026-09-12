
package com.skillmatch.user_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class InternalUserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String portfolioUrl;
    private String certifications;
    private String notes;
    private Double reputationAverage;
    private Integer reputationCount;
    private String reputationLevel;
    private List<String> skills;
}