
package com.skillmatch.contract_service.repository;

import com.skillmatch.contract_service.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {}