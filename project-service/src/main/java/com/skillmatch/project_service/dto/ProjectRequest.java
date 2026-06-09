
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

    // elenco competenze richieste (nome semplice)
    private List<String> requiredSkills;

    // livello esperienza richiesto (es. JUNIOR/MID/SENIOR)
    private String experienceLevel;
}