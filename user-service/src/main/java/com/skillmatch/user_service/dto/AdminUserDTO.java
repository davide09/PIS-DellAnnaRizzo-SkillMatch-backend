
package com.skillmatch.user_service.dto;

import lombok.Data;

@Data
public class AdminUserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;

    private boolean enabled;
    private boolean suspended;

    private Double reputationAverage;
    private Integer reputationCount;
    private String reputationLevel;
}