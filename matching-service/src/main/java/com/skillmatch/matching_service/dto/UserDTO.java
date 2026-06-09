
package com.skillmatch.matching_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<String> skills;

    //livello esperienza del professionista
    private String reputationLevel;

    //ruolo utente (ADMIN, PROFESSIONAL, COMPANY)
    private String role;
}