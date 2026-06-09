
package com.skillmatch.contract_service.service;

import com.skillmatch.contract_service.model.Contract;
import com.skillmatch.contract_service.model.Invoice;
import com.skillmatch.contract_service.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepo;

    public Invoice generateInvoice(Contract c) {
        Invoice inv = new Invoice();

        Double price = c.getPrice();
        Double commission = c.getCommission();

        if (price == null)
            throw new IllegalStateException("Impossibile generare fattura senza prezzo");

        if (commission == null)
            commission = 0.0;

        inv.setContractId(c.getId());
        inv.setProjectName(c.getProjectName());
        inv.setGrossAmount(price);
        inv.setCommissionFee(commission);
        inv.setNetAmount(price - commission);
        inv.setCreatedAt(LocalDateTime.now());

        return invoiceRepo.save(inv);
    }
}