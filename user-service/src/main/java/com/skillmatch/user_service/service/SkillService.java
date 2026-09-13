package com.skillmatch.user_service.service;

import com.skillmatch.user_service.dto.SkillDTO;
import com.skillmatch.user_service.dto.SkillRequest;
import com.skillmatch.user_service.model.Skill;
import com.skillmatch.user_service.model.User;
import com.skillmatch.user_service.model.UserRole;
import com.skillmatch.user_service.repository.SkillRepository;
import com.skillmatch.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepo;
    private final UserRepository userRepo;

    public List<SkillDTO> getSkillsByProfessional(Long professionalId) {
        User u = userRepo.findById(professionalId)
                .orElseThrow(() -> new RuntimeException("Professionista non trovato."));
        return skillRepo.findByProfessionalId(u.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<SkillDTO> addSkillToProfessional(Long professionalId, SkillRequest req) {
        User u = userRepo.findById(professionalId)
                .orElseThrow(() -> new RuntimeException("Professionista non trovato."));


        if (u.getRole() != UserRole.PROFESSIONAL)
            throw new RuntimeException("L'utente non è un professionista.");

        String[] parts = req.getName().split(",");
        List<SkillDTO> saved = new ArrayList<>();

        for (String raw : parts) {
            String name = raw.trim();
            if (name.isEmpty()) continue;

            Skill single = Skill.builder()
                    .name(name)
                    .level(req.getLevel())
                    .professional(u)
                    .build();
            saved.add(toDTO(skillRepo.save(single)));
        }

        return saved;
    }

    public void deleteSkill(Long skillId) {
        Skill s = skillRepo.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill non trovata."));
        skillRepo.delete(s);
    }



    private SkillDTO toDTO(Skill s) {
        SkillDTO dto = new SkillDTO();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setLevel(s.getLevel());
        return dto;
    }
}
