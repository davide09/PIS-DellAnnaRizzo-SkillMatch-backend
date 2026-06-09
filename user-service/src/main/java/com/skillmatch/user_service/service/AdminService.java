
package com.skillmatch.user_service.service;

import com.skillmatch.user_service.dto.AdminUserDTO;
import com.skillmatch.user_service.model.User;
import com.skillmatch.user_service.model.UserRole;
import com.skillmatch.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository repo;

    public List<AdminUserDTO> getAllUsers() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public List<AdminUserDTO> getPendingUsers() {
        return repo.findByEnabledFalse().stream()
                .map(this::toDTO)
                .toList();
    }

    public AdminUserDTO approve(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        u.setEnabled(true);
        repo.save(u);
        return toDTO(u);
    }

    public AdminUserDTO suspend(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // PROTEZIONE: Un admin NON può essere sospeso
        if (u.getRole() == UserRole.ADMIN) {
            throw new RuntimeException("L'admin non può essere sospeso.");
        }

        u.setSuspended(true);
        repo.save(u);
        return toDTO(u);
    }

    public AdminUserDTO unsuspend(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // PROTEZIONE: Un admin NON può essere riattivato
        if (u.getRole() == UserRole.ADMIN) {
            throw new RuntimeException("L'admin non può essere sospeso o modificato.");
        }

        u.setSuspended(false);
        repo.save(u);
        return toDTO(u);
    }

    private AdminUserDTO toDTO(User u) {
        AdminUserDTO dto = new AdminUserDTO();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        dto.setRole(u.getRole().name());
        dto.setEnabled(u.isEnabled());
        dto.setSuspended(u.isSuspended());
        dto.setReputationAverage(u.getReputationAverage());
        dto.setReputationCount(u.getReputationCount());
        dto.setReputationLevel(u.getReputationLevel());
        return dto;
    }
}