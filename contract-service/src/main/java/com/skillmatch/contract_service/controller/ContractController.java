
package com.skillmatch.contract_service.controller;

import com.skillmatch.contract_service.dto.ContractDTO;
import com.skillmatch.contract_service.dto.CreateProposalRequest;
import com.skillmatch.contract_service.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping("/proposals")
    public ResponseEntity<ContractDTO> createProposal(@RequestBody CreateProposalRequest request) {
        return ResponseEntity.ok(contractService.createProposal(request));
    }

    @GetMapping("/proposals/ofProfessional/{professionalId}")
    public ResponseEntity<List<ContractDTO>> getProposals(@PathVariable Long professionalId) {
        return ResponseEntity.ok(contractService.getPendingProposals(professionalId));
    }

    @PostMapping("/proposals/{id}/accept")
    public ResponseEntity<ContractDTO> accept(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.acceptProposal(id));
    }

    @PostMapping("/proposals/{id}/reject")
    public ResponseEntity<ContractDTO> reject(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.rejectProposal(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ContractDTO>> listForCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(contractService.getContractsForCompany(companyId));
    }

    @GetMapping("/professional/{professionalId}")
    public List<ContractDTO> getContractsForProfessional(@PathVariable Long professionalId) {
        return contractService.getContractsForProfessional(professionalId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getById(id));
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ContractDTO> start(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.startContract(id));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<ContractDTO> complete(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.completeContract(id));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<ContractDTO> pay(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.payContract(id));
    }

}