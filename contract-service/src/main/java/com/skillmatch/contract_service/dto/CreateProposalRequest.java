
package com.skillmatch.contract_service.dto;

import lombok.Data;
@Data
public class CreateProposalRequest {

    private Long projectId;
    private Long professionalId;
    private Long companyId;

    private String description;
    private Double price;         // per il compenso
}