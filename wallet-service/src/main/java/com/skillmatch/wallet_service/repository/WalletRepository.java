
package com.skillmatch.wallet_service.repository;

import com.skillmatch.wallet_service.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}