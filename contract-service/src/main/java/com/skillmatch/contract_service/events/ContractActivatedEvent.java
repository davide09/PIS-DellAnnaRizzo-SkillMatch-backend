
package com.skillmatch.contract_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractActivatedEvent {
    private Long contractId;
    private Long professionalId;
    private Long companyId;
}