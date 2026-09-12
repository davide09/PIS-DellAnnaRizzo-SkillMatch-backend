
// src/main/java/com/skillmatch/user_service/controller/SkillController.java
package com.skillmatch.user_service.controller;

import com.skillmatch.user_service.dto.SkillDTO;
import com.skillmatch.user_service.dto.SkillRequest;
import com.skillmatch.user_service.model.Skill;
import com.skillmatch.user_service.security.CustomUserDetails;
import com.skillmatch.user_service.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/{professionalId}/skills")
    public List<SkillDTO> getSkills(@PathVariable Long professionalId) {
        return skillService.getSkillsByProfessional(professionalId);
    }

    @PostMapping("/{professionalId}/skills")
    public List<SkillDTO> addSkill(@PathVariable Long professionalId,
                                @RequestBody SkillRequest s,
                                @AuthenticationPrincipal CustomUserDetails auth) {

        if (!auth.getUser().getId().equals(professionalId)) {
            throw new RuntimeException("Non autorizzato");
        }

        if (s.getName() == null || s.getName().trim().isEmpty()) {
            throw new RuntimeException("Nome skill mancante");
        }
        if (s.getLevel() == null || s.getLevel().trim().isEmpty()) {
            throw new RuntimeException("Livello skill mancante");
        }

        return skillService.addSkillToProfessional(professionalId, s);
    }

    @DeleteMapping("/{professionalId}/skills/{skillId}")
    public void deleteSkill(@PathVariable Long professionalId,
                            @PathVariable Long skillId,
                            @AuthenticationPrincipal CustomUserDetails auth) {

        if (!auth.getUser().getId().equals(professionalId)) {
            throw new RuntimeException("Non autorizzato");
        }

        skillService.deleteSkill(skillId);
    }
}