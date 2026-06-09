
package com.skillmatch.user_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Chi ha fatto la segnalazione
    @Column(nullable = false)
    private Long reporterId;

    // Chi è stato segnalato
    @Column(nullable = false)
    private Long reportedUserId;

    // descrizione della segnalazione
    @Column(nullable = false, length = 2000)
    private String description;

    // stato: OPEN o CLOSED
    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.OPEN;

    private LocalDateTime createdAt = LocalDateTime.now();
}