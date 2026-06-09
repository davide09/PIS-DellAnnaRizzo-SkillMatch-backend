
package com.skillmatch.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractPaidEvent {
    private Long contractId;
    private Long companyId;
    private Long professionalId;
    private Double amount;
    private Double commissionFee;
}