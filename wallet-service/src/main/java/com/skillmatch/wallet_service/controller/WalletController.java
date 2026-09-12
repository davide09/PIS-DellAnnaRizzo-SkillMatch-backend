
package com.skillmatch.wallet_service.controller;

import com.skillmatch.wallet_service.dto.DepositRequest;
import com.skillmatch.wallet_service.dto.WalletDTO;
import com.skillmatch.wallet_service.dto.WalletTransactionDTO;
import com.skillmatch.wallet_service.model.Wallet;
import com.skillmatch.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService service;


    @GetMapping("/{userId}")
    public WalletDTO getWallet(@PathVariable Long userId) {
        return service.getWalletDTO(userId);
    }


    @PostMapping("/{userId}/deposit")
    public WalletDTO deposit(@PathVariable Long userId, @RequestBody DepositRequest req) {
        Wallet w = service.deposit(userId, req.getAmount());
        WalletDTO dto = new WalletDTO();
        dto.setUserId(w.getUserId());
        dto.setBalance(w.getBalance());
        return dto;
    }


    @GetMapping("/{userId}/transactions")
    public List<WalletTransactionDTO> getTransactions(@PathVariable Long userId) {
        return service.getWalletTransactions(userId);
    }
}