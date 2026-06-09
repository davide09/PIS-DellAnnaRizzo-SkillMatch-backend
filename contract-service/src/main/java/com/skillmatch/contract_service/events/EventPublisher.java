
package com.skillmatch.contract_service.events;

import com.skillmatch.events.ContractPaidEvent;
import com.skillmatch.contract_service.config.RabbitConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;

    @PostConstruct
    void init() {
        rabbitTemplate.setMessageConverter(messageConverter);
    }

    // ============================================================
    //  EVENTI PROPOSTA
    // ============================================================

    public void publishProposalCreated(ContractProposalCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                "contract.proposal.created",
                event
        );
    }

    public void publishAccepted(ContractAcceptedEvent event) {
        rabbitTemplate.convertAndSend(
                "contract.accepted",
                event
        );
    }

    public void publishRejected(ContractRejectedEvent event) {
        rabbitTemplate.convertAndSend(
                "contract.rejected",
                event
        );
    }

    public void publishActivated(ContractActivatedEvent event) {
        rabbitTemplate.convertAndSend(
                "contract.activated",
                event
        );
    }

    // ============================================================
    //  EVENTO PAGAMENTO CONTRATTO
    //  → Wallet Service
    // ============================================================

    public void publishPaid(ContractPaidEvent event) {

        // Evento verso wallet-service
        rabbitTemplate.convertAndSend(
                "wallet-exchange",
                "contract.paid",
                event
        );
    }
}