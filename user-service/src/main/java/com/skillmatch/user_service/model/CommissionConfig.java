
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


    @Column(nullable = false)
    private Double percentage;
}