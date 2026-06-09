
package com.skillmatch.wallet_service.dto;

import com.skillmatch.wallet_service.model.WalletTransactionType;
import lombok.Data;

@Data
public class AdminTransactionDTO {

    private Long userId;                // su quale wallet applicare la transazione
    private Double amount;              // importo della transazione
    private WalletTransactionType type; // CREDIT / COMMISSION_FEE
    private String note;                // opzionale - per motivi amministrativi
}