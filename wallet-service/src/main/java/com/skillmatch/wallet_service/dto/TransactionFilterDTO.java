
package com.skillmatch.wallet_service.dto;

import lombok.Data;

@Data
public class TransactionFilterDTO {

    private Long userId;          // opzionale - filtra per utente specifico
    private String type;          // CREDIT | COMMISSION_FEE
    private String fromDate;      // formato ISO: "2025-12-01T00:00:00"
    private String toDate;        // formato ISO: "2025-12-31T23:59:59"
    private Double minAmount;     // minimo importo
    private Double maxAmount;     // massimo importo
}