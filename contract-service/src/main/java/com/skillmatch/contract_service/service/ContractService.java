
package com.skillmatch.contract_service.service;

import com.skillmatch.contract_service.client.CommissionClient;
import com.skillmatch.events.ContractPaidEvent;
import com.skillmatch.contract_service.client.ProjectClient;
import com.skillmatch.contract_service.dto.ContractDTO;
import com.skillmatch.contract_service.dto.CreateProposalRequest;
import com.skillmatch.contract_service.events.*;
import com.skillmatch.contract_service.model.Contract;
import com.skillmatch.contract_service.model.ContractStatus;
import com.skillmatch.contract_service.model.Invoice;
import com.skillmatch.contract_service.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final EventPublisher eventPublisher;
    private final InvoiceService invoiceService;
    private final ProjectClient projectClient;
    private final CommissionClient commissionClient;

    // ----------------------------------------------------
    // 1. Creazione proposta
    // ----------------------------------------------------
    public ContractDTO createProposal(CreateProposalRequest request) {

        Contract c = new Contract();
        c.setProjectId(request.getProjectId());
        c.setProfessionalId(request.getProfessionalId());
        c.setCompanyId(request.getCompanyId());

        String projectName = projectClient.getProjectTitle(request.getProjectId());
        c.setProjectName(projectName);

        String desc = (request.getDescription() != null && !request.getDescription().isBlank())
                ? request.getDescription()
                : "Proposta inviata al professionista";
        c.setDescription(desc);

        Double price = request.getPrice();
        c.setPrice(price);

        if (price != null) {
            double percentage = commissionClient.getCommissionPercentage();
            c.setCommission((price * percentage) / 100);
        }

        c.setStatus(ContractStatus.PENDING);

        Contract saved = contractRepository.save(c);

        eventPublisher.publishProposalCreated(
                new ContractProposalCreatedEvent(
                        saved.getId(),
                        saved.getProfessionalId(),
                        saved.getProjectId(),
                        saved.getCompanyId()
                )
        );

        return toDTO(saved);
    }

    // ----------------------------------------------------
    public List<ContractDTO> getPendingProposals(Long professionalId) {
        return contractRepository
                .findByProfessionalIdAndStatus(professionalId, ContractStatus.PENDING)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ----------------------------------------------------
    public ContractDTO acceptProposal(Long id) {
        Contract c = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        if (c.getStatus() != ContractStatus.PENDING)
            throw new RuntimeException("Contract is not PENDING");

        c.setStatus(ContractStatus.ACCEPTED);

        Contract saved = contractRepository.save(c);

        eventPublisher.publishAccepted(
                new ContractAcceptedEvent(
                        saved.getId(),
                        saved.getProfessionalId(),
                        saved.getCompanyId()
                )
        );

        return toDTO(saved);
    }

    // ----------------------------------------------------
    public ContractDTO rejectProposal(Long id) {
        Contract c = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        c.setStatus(ContractStatus.REJECTED);

        Contract saved = contractRepository.save(c);

        eventPublisher.publishRejected(
                new ContractRejectedEvent(
                        saved.getId(),
                        saved.getProfessionalId(),
                        saved.getCompanyId()
                )
        );

        return toDTO(saved);
    }

    // ----------------------------------------------------
    private ContractDTO toDTO(Contract c) {

        Double commission = c.getCommission();
        if (commission == null && c.getPrice() != null ) {
            double percentage = commissionClient.getCommissionPercentage();
            commission = ((c.getPrice() * percentage) / 100);
        }

        return new ContractDTO(
                c.getId(),
                c.getProjectId(),
                c.getProjectName(),
                c.getProfessionalId(),
                c.getCompanyId(),
                c.getDescription(),
                c.getStatus(),
                c.getPrice(),
                commission,
                c.getInvoiceId(),
                c.getCreatedAt() != null ? c.getCreatedAt().toString() : null,
                c.getUpdatedAt() != null ? c.getUpdatedAt().toString() : null
        );
    }

    // ----------------------------------------------------
    public List<ContractDTO> getContractsForCompany(Long companyId) {
        return contractRepository.findByCompanyIdOrderByCreatedAtDesc(companyId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ContractDTO> getContractsForProfessional(Long professionalId) {
        return contractRepository.findByProfessionalIdOrderByCreatedAtDesc(professionalId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ----------------------------------------------------
    public ContractDTO getById(Long id) {
        return toDTO(contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found")));
    }

    // ----------------------------------------------------
    public ContractDTO startContract(Long id) {
        Contract c = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        if (c.getStatus() != ContractStatus.ACCEPTED)
            throw new RuntimeException("Contract not ACCEPTED");

        c.setStatus(ContractStatus.ACTIVE);
        Contract saved = contractRepository.save(c);

        eventPublisher.publishActivated(
                new ContractActivatedEvent(
                        saved.getId(),
                        saved.getProfessionalId(),
                        saved.getCompanyId()
                )
        );

        return toDTO(saved);
    }

    // ----------------------------------------------------
    public ContractDTO completeContract(Long id) {
        Contract c = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        if (c.getStatus() != ContractStatus.ACTIVE)
            throw new RuntimeException("Contract not ACTIVE");

        c.setStatus(ContractStatus.COMPLETED);
        return toDTO(contractRepository.save(c));
    }

    // ----------------------------------------------------
    public ContractDTO payContract(Long id) {

        Contract c = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        if (c.getStatus() != ContractStatus.COMPLETED)
            throw new RuntimeException("Contract not COMPLETED");

        if (c.getPrice() == null)
            throw new RuntimeException("Impossibile pagare un contratto senza prezzo.");

        // calcola commissione se assente
        if (c.getCommission() == null) {
            double percentage = commissionClient.getCommissionPercentage();
            c.setCommission((c.getPrice() * percentage) / 100);
        }

        // genera prima la fattura
        Invoice invoice = invoiceService.generateInvoice(c);
        c.setInvoiceId(invoice.getId());

        // aggiorna lo stato DOPO che tutti i dati sono completi
        c.setStatus(ContractStatus.PAID);

        Contract saved = contractRepository.save(c);

        // PUBBLICA EVENTO DI PAGAMENTO
        eventPublisher.publishPaid(
                new ContractPaidEvent(
                        saved.getId(),
                        saved.getCompanyId(),
                        saved.getProfessionalId(),
                        saved.getPrice(),
                        saved.getCommission()
                )
        );

        return toDTO(saved);
    }
}