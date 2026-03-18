package com.felixlm.authmodule.api.dto;

import java.util.UUID;

public record AuthTokenResponse(
    UUID userId,
    String email,
    String fullName,
    String token,
    String tokenType,
    long expiresIn
) {}

