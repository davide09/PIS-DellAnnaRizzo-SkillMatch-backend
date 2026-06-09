
package com.skillmatch.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long id;         // ID utente
    private Long companyId;  // SOLO se role = COMPANY
    private String name;
    private String email;
    private String role;
    private String token;
}
