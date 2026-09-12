
package com.skillmatch.wallet_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "wallet_transactions",
        indexes = {
                @Index(name = "idx_wallet_id", columnList = "wallet_id"),
                @Index(name = "idx_tx_type", columnList = "type"),
                @Index(name = "idx_contract_wallet_type", columnList = "contract_id, wallet_id, type")
        }
)
@Data
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletId;

    private Double amount;

    @Column(nullable = false)
    private String type;

    private String note;

    private Long contractId;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }
}