
package com.skillmatch.project_service.events;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractEvent {

    private Long contractId;
    private String eventType; // CONTRACT_CREATED / CONTRACT_STARTED / CONTRACT_COMPLETED / CONTRACT_PAID ...
    private LocalDateTime timestamp;

    private Long projectId;
    private Long companyId;
    private Long professionalId;
}