
package com.skillmatch.wallet_service.dto;

import com.skillmatch.wallet_service.model.WalletTransactionType;
import lombok.Data;

@Data
public class AdminTransactionDTO {

    private Long userId;
    private Double amount;
    private WalletTransactionType type;
    private String note;
}