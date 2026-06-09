package com.skillmatch.contract_service.controller;

import com.skillmatch.contract_service.dto.InvoiceDTO;
import com.skillmatch.contract_service.model.Invoice;
import com.skillmatch.contract_service.repository.InvoiceRepository;
import com.skillmatch.contract_service.service.InvoicePdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/contracts/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceRepository invoiceRepo;
    private final InvoicePdfService pdfService;

    // restituisce InvoiceDTO invece di Invoice grezzo
    @GetMapping("/{id}")
    public InvoiceDTO getById(@PathVariable Long id) {
        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return toDTO(invoice);
    }

    // il PDF non cambia: pdfService lavora sull'entità internamente,
    // non espone nulla verso il client (restituisce byte[])
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        byte[] pdf = pdfService.generateInvoicePdf(invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition
                        .attachment()
                        .filename(("fattura-" + id + ".pdf"), StandardCharsets.UTF_8)
                        .build()
        );

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    // -------------------------------------------------------
    //  METODI PRIVATI
    // -------------------------------------------------------

    private InvoiceDTO toDTO(Invoice inv) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(inv.getId());
        dto.setContractId(inv.getContractId());
        dto.setProjectName(inv.getProjectName());
        dto.setGrossAmount(inv.getGrossAmount());
        dto.setCommissionFee(inv.getCommissionFee());
        dto.setNetAmount(inv.getNetAmount());
        dto.setCreatedAt(inv.getCreatedAt());
        return dto;
    }
}
