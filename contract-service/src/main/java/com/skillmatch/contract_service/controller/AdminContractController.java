
package com.skillmatch.contract_service.controller;

import com.skillmatch.contract_service.dto.ContractDTO;
import com.skillmatch.contract_service.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts/admin")
@RequiredArgsConstructor
public class AdminContractController {

    private final ContractRepository repo;

    @GetMapping
    public List<ContractDTO> all() {
        return repo.findAll()
                .stream()
                .map(c -> new ContractDTO(
                        c.getId(),
                        c.getProjectId(),
                        c.getProjectName(),
                        c.getProfessionalId(),
                        c.getCompanyId(),
                        c.getDescription(),
                        c.getStatus(),
                        c.getPrice(),
                        c.getCommission(),
                        c.getInvoiceId(),
                        c.getCreatedAt().toString(),
                        c.getUpdatedAt().toString()
                ))
                .toList();
    }
}