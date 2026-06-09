
package com.skillmatch.user_service.controller;

import com.skillmatch.user_service.dto.AuthResponse;
import com.skillmatch.user_service.dto.LoginRequest;
import com.skillmatch.user_service.dto.RegisterRequest;
import com.skillmatch.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
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

    // TOKEN INTERNO PER ALTRI MICROSERVIZI
    @GetMapping("/internal-token")
    public String getInternalToken() {
        return authService.generateInternalToken();
    }
}