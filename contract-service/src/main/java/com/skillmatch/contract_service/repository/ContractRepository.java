
package com.skillmatch.contract_service.repository;

import com.skillmatch.contract_service.model.Contract;
import com.skillmatch.contract_service.model.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByProfessionalIdAndStatus(Long professionalId, ContractStatus status);

    List<Contract> findByCompanyIdOrderByCreatedAtDesc(Long companyId);

    List<Contract> findByProfessionalIdOrderByCreatedAtDesc(Long professionalId);
}