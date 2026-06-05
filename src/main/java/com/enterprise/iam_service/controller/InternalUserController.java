package com.enterprise.iam_service.controller;

import com.enterprise.iam_service.dto.UserProfileResponse;
import com.enterprise.iam_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


// * users by ID. Requires a valid JWT — the calling service forwards the user's token.
//

// ! Also update SecurityConfig to permit /api/internal/** only from known service IPs
@RestController
@RequestMapping("/api/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserRepository userRepository;

    // GET /api/internal/users/{id}
    // * Returns a safe, minimal view of a user by UUID.
    // * Used by the friend-service to resolve user emails for friend list responses.
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(new UserLookupResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getStatus()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // * Minimal record — never exposes passwordHash or internal fields
    public record UserLookupResponse(UUID id, String name, String email, String status) {}
}