
package com.skillmatch.contract_service.service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.skillmatch.contract_service.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InvoicePdfService {

    public byte[] generateInvoicePdf(Invoice invoice) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);

            doc.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // FORMATTAZIONE DATA IN EUROPE/ROME
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            ZonedDateTime romeTime = invoice.getCreatedAt()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of("Europe/Rome"));
            String formattedDate = fmt.format(romeTime);

            // PDF CONTENT
            doc.add(new Paragraph("Fattura SkillMatch", titleFont));
            doc.add(new Paragraph(" ", normalFont));

            doc.add(new Paragraph("Numero fattura: " + invoice.getId(), normalFont));
            doc.add(new Paragraph("Progetto: " + invoice.getProjectName()
                    + " (Contratto #" + invoice.getContractId() + ")", normalFont));
            doc.add(new Paragraph("Data creazione: " + formattedDate, normalFont));
            doc.add(new Paragraph(" ", normalFont));

            doc.add(new Paragraph("Importo lordo: " + invoice.getGrossAmount() + " €", normalFont));
            doc.add(new Paragraph("Commissione SkillMatch: " + invoice.getCommissionFee() + " €", normalFont));
            doc.add(new Paragraph("Importo netto al professionista: " + invoice.getNetAmount() + " €", normalFont));

            doc.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Errore generazione PDF: " + e.getMessage(), e);
        }
    }
}