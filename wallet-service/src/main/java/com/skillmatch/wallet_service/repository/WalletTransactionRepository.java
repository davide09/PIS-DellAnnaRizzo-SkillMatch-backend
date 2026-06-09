
package com.skillmatch.wallet_service.repository;

import com.skillmatch.wallet_service.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByWalletIdOrderByCreatedAtDesc(Long walletId);
    boolean existsByWalletIdAndContractIdAndType(Long walletId, Long contractId, String type);

}