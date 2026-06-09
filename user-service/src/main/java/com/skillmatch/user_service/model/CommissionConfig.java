
package com.skillmatch.user_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "commission_config")
@Data
public class CommissionConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // percentuale es: 8 significa 8%
    @Column(nullable = false)
    private Double percentage;
}