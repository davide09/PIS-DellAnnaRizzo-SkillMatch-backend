package com.skillmatch.user_service.controller;

import com.skillmatch.user_service.dto.*;
import com.skillmatch.user_service.repository.UserRepository;
import com.skillmatch.user_service.security.CustomUserDetails;
import com.skillmatch.user_service.service.FeedbackService;
import com.skillmatch.user_service.service.SkillService;
import com.skillmatch.user_service.service.UserService;
import com.skillmatch.user_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repo;
    private final FeedbackService feedbackService;
    private final SkillService skillService;
    private final UserService userService;



    @GetMapping("/internal")
    public List<InternalUserDTO> internalGetAll() {
        return repo.findAll().stream().map(u -> {
            InternalUserDTO dto = new InternalUserDTO();
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
            dto.setSkills(
                    u.getSkills().stream()
                            .map(s -> s.getName())
                            .toList()
            );
            return dto;
        }).toList();
    }

    @GetMapping("/internal/{id}/skills")
    public List<SkillDTO> internalGetSkills(@PathVariable Long id) {
        return skillService.getSkillsByProfessional(id);
    }

    @GetMapping("/internal/{id}/feedback")
    public double internalGetReputation(@PathVariable Long id) {
        return feedbackService.getSummary(id).getAverage();
    }



    @GetMapping
    public List<UserDTO> getAllUsers() {
        return repo.findAll().stream()
                .map(userService::toDTO)
                .toList();
    }

    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User u = repo.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato."));
        return userService.toDTO(u);
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato."));
        return userService.toDTO(u);
    }


    @PutMapping("/{id}")
    public UserDTO update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest data,
            @AuthenticationPrincipal CustomUserDetails auth
    ) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (!u.getId().equals(auth.getUser().getId())) {
            throw new RuntimeException("Non autorizzato");
        }

        if (data.getPortfolioUrl() != null)   u.setPortfolioUrl(data.getPortfolioUrl());
        if (data.getCertifications() != null) u.setCertifications(data.getCertifications());
        if (data.getNotes() != null)          u.setNotes(data.getNotes());

        return userService.toDTO(repo.save(u));
    }

    @PostMapping("/{id}/feedback")
    public FeedbackDTO addFeedback(
            @PathVariable Long id,
            @RequestBody FeedbackRequest req,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long raterId = userDetails != null ? userDetails.getUser().getId() : null;
        if (raterId != null && raterId.equals(id)) {
            throw new RuntimeException("Non puoi lasciare un feedback a te stesso.");
        }
        if (req.getRating() < 1 || req.getRating() > 5) {
            throw new RuntimeException("Il voto deve essere compreso tra 1 e 5.");
        }
        req.setProfessionalId(id);
        return feedbackService.createFeedback(raterId, req);
    }

    @GetMapping("/{id}/feedback/list")
    public List<FeedbackDTO> getAllFeedback(@PathVariable Long id) {
        User p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return feedbackService.getAllFeedbackForProfessional(p);
    }

    @GetMapping("/feedback/{id}")
    public double getReputation(@PathVariable Long id) {
        return feedbackService.getSummary(id).getAverage();
    }

    @GetMapping("/feedback/{id}/summary")
    public FeedbackSummary getFeedbackSummary(@PathVariable Long id) {
        return feedbackService.getSummary(id);
    }
}