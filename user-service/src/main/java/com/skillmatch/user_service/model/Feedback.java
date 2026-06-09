
// src/main/java/com/skillmatch/user_service/model/Feedback.java
package com.skillmatch.user_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    private User professional;

    private Long raterId;      // chi ha dato il voto (azienda o professionista)
    private int rating;        // 1..5
    private String comment;

    private LocalDateTime createdAt;
}