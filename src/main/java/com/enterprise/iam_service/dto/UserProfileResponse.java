package com.enterprise.iam_service.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


public record UserProfileResponse(
    UUID id,
    String name,
    String email, 
    String status,
    Set<String> roles,
    LocalDateTime lastLogin,
    LocalDateTime createdAt
) {}