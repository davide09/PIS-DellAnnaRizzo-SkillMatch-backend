
package com.skillmatch.wallet_service.controller;

import com.skillmatch.wallet_service.dto.TransactionFilterDTO;
import com.skillmatch.wallet_service.dto.WalletTransactionDTO;
import com.skillmatch.wallet_service.service.TransactionFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/wallet/transactions/filter")
@RequiredArgsConstructor
public class TransactionFilterController {

    private final TransactionFilterService service;

    @PostMapping
    public List<WalletTransactionDTO> filter(@RequestBody TransactionFilterDTO req) {

        return service.filter(req)
                .stream()
                .map(tx -> new WalletTransactionDTO(
                        tx.getId(),
                        tx.getWalletId(),
                        tx.getAmount(),
                        tx.getType(),
                        tx.getCreatedAt().toString()
                ))
                .toList();
    }
}