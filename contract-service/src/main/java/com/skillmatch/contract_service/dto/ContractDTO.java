
package com.skillmatch.contract_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillmatch.contract_service.model.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContractDTO {

    private Long id;
    private Long projectId;

    private String projectName;

    private Long professionalId;
    private Long companyId;

    private String description;
    private ContractStatus status;

    private Double price;

    @JsonProperty("commissionFee")
    private Double commission;

    private Long invoiceId;

    private String createdAt;
    private String updatedAt;
}