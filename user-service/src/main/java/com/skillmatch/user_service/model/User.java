
package com.skillmatch.user_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Long companyId;

    private Double reputationAverage;
    private Integer reputationCount;
    private String reputationLevel;

    private String portfolioUrl;
    private String certifications;
    private String notes;

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Skill> skills;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean enabled = false;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean suspended = false;

}
