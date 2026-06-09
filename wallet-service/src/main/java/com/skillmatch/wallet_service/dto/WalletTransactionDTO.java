
package com.skillmatch.wallet_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionDTO {
    private Long id;
    private Long walletId;
    private Double amount;
    private String type;
    private String createdAt;
}