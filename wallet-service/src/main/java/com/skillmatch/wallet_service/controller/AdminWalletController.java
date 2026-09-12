
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
@RequestMapping("/api/admin/wallet")
@RequiredArgsConstructor
public class AdminWalletController {

    private final WalletService walletService;


    @GetMapping
    public List<WalletDTO> listAllWallets() {
        return walletService.getAllWallets()
                .stream()
                .map(w -> {
                    WalletDTO dto = new WalletDTO();
                    dto.setUserId(w.getUserId());
                    dto.setBalance(w.getBalance());
                    return dto;
                })
                .toList();
    }


    @GetMapping("/transactions")
    public List<WalletTransactionDTO> listAllTransactions() {
        return walletService.getAllTransactions()
                .stream()
                .map(tx -> {
                    WalletTransactionDTO dto = new WalletTransactionDTO();
                    dto.setId(tx.getId());
                    dto.setWalletId(tx.getWalletId());
                    dto.setAmount(tx.getAmount());
                    dto.setType(tx.getType());
                    dto.setCreatedAt(tx.getCreatedAt().toString());
                    return dto;
                })
                .toList();
    }


    @GetMapping("/transactions/{type}")
    public List<WalletTransactionDTO> listTransactionsByType(@PathVariable String type) {
        return walletService.getTransactionsByType(type.toUpperCase())
                .stream()
                .map(tx -> {
                    WalletTransactionDTO dto = new WalletTransactionDTO();
                    dto.setId(tx.getId());
                    dto.setWalletId(tx.getWalletId());
                    dto.setAmount(tx.getAmount());
                    dto.setType(tx.getType());
                    dto.setCreatedAt(tx.getCreatedAt().toString());
                    return dto;
                })
                .toList();
    }


    @PostMapping("/{userId}/force-deposit")
    public WalletDTO adminDeposit(
            @PathVariable Long userId,
            @RequestBody DepositRequest req
    ) {
        Wallet w = walletService.deposit(userId, req.getAmount());

        WalletDTO dto = new WalletDTO();
        dto.setUserId(w.getUserId());
        dto.setBalance(w.getBalance());
        return dto;
    }


    @PostMapping("/{userId}/reset")
    public WalletDTO resetWallet(@PathVariable Long userId) {
        Wallet w = walletService.resetWallet(userId);

        WalletDTO dto = new WalletDTO();
        dto.setUserId(w.getUserId());
        dto.setBalance(w.getBalance());
        return dto;
    }


    @DeleteMapping("/{userId}")
    public String deleteWallet(@PathVariable Long userId) {
        walletService.deleteWallet(userId);
        return "Wallet dell'utente " + userId + " eliminato.";
    }
}