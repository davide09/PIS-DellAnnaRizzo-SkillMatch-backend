
package com.skillmatch.user_service.controller;

import com.skillmatch.user_service.dto.AuthResponse;
import com.skillmatch.user_service.dto.LoginRequest;
import com.skillmatch.user_service.dto.RegisterRequest;
import com.skillmatch.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    // TOKEN INTERNO PER ALTRI MICROSERVIzi
    @GetMapping("/internal-token")
    public String getInternalToken() {
        return authService.generateInternalToken();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        authService.logout(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}