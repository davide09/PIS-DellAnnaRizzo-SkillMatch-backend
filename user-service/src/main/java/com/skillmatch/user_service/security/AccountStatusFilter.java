
package com.skillmatch.user_service.security;

import com.skillmatch.user_service.model.User;
import com.skillmatch.user_service.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccountStatusFilter extends OncePerRequestFilter {

    private final UserRepository repo;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Viene settato da JwtAuthFilter
        String email = (String) request.getAttribute("email");

        if (email != null) {
            User u = repo.findByEmail(email).orElse(null);

            if (u != null) {
                if (!u.isEnabled()) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Account non approvato.");
                    return;
                }
                if (u.isSuspended()) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Account sospeso.");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}