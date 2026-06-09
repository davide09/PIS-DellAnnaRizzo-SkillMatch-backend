
package com.skillmatch.user_service.controller;

import com.skillmatch.user_service.dto.AdminUserDTO;
import com.skillmatch.user_service.dto.FeedbackDTO;
import com.skillmatch.user_service.model.Feedback;
import com.skillmatch.user_service.model.User;
import com.skillmatch.user_service.repository.UserRepository;
import com.skillmatch.user_service.service.AdminService;
import com.skillmatch.user_service.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminService service;
    private final UserRepository repo;
    private final FeedbackService feedbackService;

    @GetMapping
    public List<AdminUserDTO> getAll() {
        return service.getAllUsers();
    }

    @GetMapping("/pending")
    public List<AdminUserDTO> getPending() {
        return service.getPendingUsers();
    }

    @PostMapping("/{id}/approve")
    public AdminUserDTO approve(@PathVariable Long id) {
        return service.approve(id);
    }

    @PostMapping("/{id}/suspend")
    public AdminUserDTO suspend(@PathVariable Long id) {
        return service.suspend(id);
    }

    @PostMapping("/{id}/unsuspend")
    public AdminUserDTO unsuspend(@PathVariable Long id) {
        return service.unsuspend(id);
    }

    @GetMapping("/{id}/feedback")
    public List<FeedbackDTO> adminGetFeedback(@PathVariable Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        return feedbackService.getAllFeedbackForProfessional(u);
    }
}