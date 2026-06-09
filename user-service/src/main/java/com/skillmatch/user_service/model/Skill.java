
// src/main/java/com/skillmatch/user_service/model/Skill.java
package com.skillmatch.user_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;   // es. "Java", "React"
    private String level;  // "JUNIOR", "MEDIUM", "SENIOR" (come nel FE)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    @JsonBackReference
    private User professional;
}