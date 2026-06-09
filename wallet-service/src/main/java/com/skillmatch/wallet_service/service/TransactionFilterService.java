
package com.skillmatch.wallet_service.service;

import com.skillmatch.wallet_service.dto.TransactionFilterDTO;
import com.skillmatch.wallet_service.model.WalletTransaction;
import com.skillmatch.wallet_service.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionFilterService {

    private final WalletTransactionRepository txRepo;

    public List<WalletTransaction> filter(TransactionFilterDTO f) {

        return txRepo.findAll()
                .stream()
                .filter(tx -> f.getUserId() == null || tx.getWalletId().equals(f.getUserId()))
                .filter(tx -> f.getType() == null ||
                        f.getType().isBlank() ||
                        tx.getType().equalsIgnoreCase(f.getType()))
                .filter(tx -> f.getMinAmount() == null || tx.getAmount() >= f.getMinAmount())
                .filter(tx -> f.getMaxAmount() == null || tx.getAmount() <= f.getMaxAmount())
                .filter(tx -> f.getFromDate() == null ||
                        f.getFromDate().isBlank() ||
                        tx.getCreatedAt().isAfter(LocalDateTime.parse(f.getFromDate() + ":00")))
                .filter(tx -> f.getToDate() == null ||
                        f.getToDate().isBlank() ||
                        tx.getCreatedAt().isBefore(LocalDateTime.parse(f.getToDate() + ":00")))
                .toList();
    }
}