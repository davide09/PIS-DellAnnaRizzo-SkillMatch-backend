package com.skillmatch.user_service.service;

import com.skillmatch.user_service.dto.SkillDTO;
import com.skillmatch.user_service.dto.UserDTO;
import com.skillmatch.user_service.model.User;
import com.skillmatch.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;

    public Optional<User> getById(Long id) {
        return repo.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return repo.findByEmail(email);
    }

    public UserDTO toDTO(User u) {
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        dto.setRole(u.getRole().name());
        dto.setReputationAverage(u.getReputationAverage());
        dto.setReputationCount(u.getReputationCount());
        dto.setReputationLevel(u.getReputationLevel());
        dto.setPortfolioUrl(u.getPortfolioUrl());
        dto.setCertifications(u.getCertifications());
        dto.setNotes(u.getNotes());
        List<SkillDTO> skills = u.getSkills() == null ? List.of() :
                u.getSkills().stream().map(s -> {
                    SkillDTO sd = new SkillDTO();
                    sd.setId(s.getId());
                    sd.setName(s.getName());
                    sd.setLevel(s.getLevel());
                    return sd;
                }).toList();

        dto.setSkills(skills);
        return dto;
    }
}