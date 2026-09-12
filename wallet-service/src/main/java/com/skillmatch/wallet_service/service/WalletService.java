
package com.skillmatch.wallet_service.service;

import com.skillmatch.wallet_service.dto.WalletDTO;
import com.skillmatch.wallet_service.dto.WalletTransactionDTO;
import com.skillmatch.wallet_service.model.Wallet;
import com.skillmatch.wallet_service.model.WalletTransaction;
import com.skillmatch.wallet_service.model.WalletTransactionType;
import com.skillmatch.wallet_service.repository.WalletRepository;
import com.skillmatch.wallet_service.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repo;
    private final WalletTransactionRepository txRepo;


    public Wallet getWallet(Long userId) {
        return repo.findById(userId).orElse(null);
    }


    public Wallet getOrCreateWallet(Long userId) {
        return repo.findById(userId).orElseGet(() -> {
            Wallet w = new Wallet();
            w.setUserId(userId);
            w.setBalance(0.0);
            w.setCreatedAt(LocalDateTime.now());
            return repo.save(w);
        });
    }


    public Wallet deposit(Long userId, Double amount) {
        if (amount == null || amount <= 0)
            throw new IllegalArgumentException("L'importo deve essere maggiore di zero.");

        Wallet wallet = getOrCreateWallet(userId);
        wallet.setBalance(wallet.getBalance() + amount);
        repo.save(wallet);

        recordTransaction(userId, amount, WalletTransactionType.CREDIT);
        return wallet;
    }


    private void recordTransaction(Long userId, Double amount, WalletTransactionType type) {
        WalletTransaction tx = new WalletTransaction();
        tx.setWalletId(userId);
        tx.setAmount(amount);
        tx.setType(type.name());
        tx.setCreatedAt(LocalDateTime.now());
        txRepo.save(tx);
    }


    public WalletDTO getWalletDTO(Long userId) {
        Wallet w = getWallet(userId);
        WalletDTO dto = new WalletDTO();
        dto.setUserId(userId);
        dto.setBalance(w != null ? w.getBalance() : 0.0);
        return dto;
    }


    public List<WalletTransactionDTO> getWalletTransactions(Long userId) {
        return txRepo.findByWalletIdOrderByCreatedAtDesc(userId)
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


    public List<Wallet> getAllWallets() {
        return repo.findAll();
    }


    public List<WalletTransaction> getAllTransactions() {
        return txRepo.findAll();
    }


    public List<WalletTransaction> getTransactionsByType(String type) {
        return txRepo.findAll()
                .stream()
                .filter(tx -> tx.getType().equalsIgnoreCase(type))
                .toList();
    }


    public Wallet resetWallet(Long userId) {
        Wallet w = getOrCreateWallet(userId);
        w.setBalance(0.0);
        return repo.save(w);
    }


    public void deleteWallet(Long userId) {
        repo.deleteById(userId);
    }


    public Wallet applyAdminTransaction(Long userId, Double amount,
                                        WalletTransactionType type, String note, Long contractId) {

        if (amount == null || amount <= 0)
            throw new IllegalArgumentException("Importo non valido");

        if (contractId != null &&
                txRepo.existsByWalletIdAndContractIdAndType(userId, contractId, type.name())) {
            return getOrCreateWallet(userId);
        }

        Wallet wallet = getOrCreateWallet(userId);

        wallet.setBalance(wallet.getBalance() + amount);

        repo.save(wallet);

        WalletTransaction tx = new WalletTransaction();
        tx.setWalletId(userId);
        tx.setAmount(amount);
        tx.setType(type.name());
        tx.setNote(note);
        tx.setContractId(contractId);
        tx.setCreatedAt(LocalDateTime.now());
        txRepo.save(tx);

        return wallet;
    }

}