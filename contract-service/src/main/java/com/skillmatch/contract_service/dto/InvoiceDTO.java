package com.skillmatch.contract_service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InvoiceDTO {
    private Long id;
    private Long contractId;
    private String projectName;
    private Double grossAmount;
    private Double commissionFee;
    private Double netAmount;
    private LocalDateTime createdAt;
}
