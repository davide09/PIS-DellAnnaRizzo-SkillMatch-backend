
package com.skillmatch.user_service.service;

import com.skillmatch.user_service.dto.AuthResponse;
import com.skillmatch.user_service.dto.LoginRequest;
import com.skillmatch.user_service.dto.RegisterRequest;
import com.skillmatch.user_service.model.User;
import com.skillmatch.user_service.model.UserRole;
import com.skillmatch.user_service.repository.UserRepository;
import com.skillmatch.user_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwt;

    public AuthResponse login(LoginRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenziali errate.");
        }

        // controllo se l'utente/compagnia è approvato/a dall'admin
        if (!user.isEnabled()) {
            throw new RuntimeException("Account non approvato.");
        }

        // controllo se l'utente/compagnia è sospeso/a dall'admin
        if(user.isSuspended()) {
            throw new RuntimeException("Account sospeso.");
        }

        String token = jwt.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                user.getId(),
                user.getCompanyId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                token
        );
    }

    public AuthResponse register(RegisterRequest req) {

        User saved = repo.save(
                User.builder()
                        .name(req.getName())
                        .email(req.getEmail())
                        .password(encoder.encode(req.getPassword()))
                        .role(UserRole.valueOf(req.getRole()))  // COMPANY / PROFESSIONAL
                        .enabled(false)
                        .build()
        );

        String token = jwt.generateToken(saved.getEmail(), saved.getRole().name());

        return new AuthResponse(
                saved.getId(),
                saved.getCompanyId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole().name(),
                token
        );

    }

    // USATO SOLO DAL MATCHING-SERVICE E ALTRI MICROSERVIZI
    public String generateInternalToken() {
        return jwt.generateToken("SYSTEM", "INTERNAL");
    }
}