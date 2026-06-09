
package com.skillmatch.contract_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;

    private Long contractId;
    private Double grossAmount;
    private Double commissionFee;
    private Double netAmount;

    private LocalDateTime createdAt;
}