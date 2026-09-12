
package com.skillmatch.wallet_service.dto;

import lombok.Data;

@Data
public class TransactionFilterDTO {

    private Long userId;
    private String type;
    private String fromDate;      // formato ISO: "2025-12-01T00:00:00"
    private String toDate;        // formatoISO: "2025-12-31T23:59:59"
    private Double minAmount;
    private Double maxAmount;
}