
package com.skillmatch.wallet_service.listener;

import com.skillmatch.events.ContractPaidEvent;
import com.skillmatch.wallet_service.model.WalletTransactionType;
import com.skillmatch.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractPaidEventListener {

    private final WalletService walletService;

    @RabbitListener(queues = "contract-paid-queue")
    public void handleContractPaid(ContractPaidEvent event) {

        Long contractId = event.getContractId();


        walletService.applyAdminTransaction(
                event.getProfessionalId(),
                event.getAmount() - event.getCommissionFee(),
                WalletTransactionType.CREDIT,
                "Pagamento ricevuto per contratto #" + contractId,
                contractId
        );

        walletService.applyAdminTransaction(
                -1L,
                event.getCommissionFee(),
                WalletTransactionType.COMMISSION_FEE,
                "Commissione per contratto #" + contractId,
                contractId
        );
    }
}