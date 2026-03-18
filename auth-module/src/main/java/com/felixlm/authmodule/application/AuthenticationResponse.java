package com.felixlm.authmodule.application;

import java.util.UUID;

public record AuthenticationResponse(
    UUID userId,
    String email,
    String fullName,
    String token
) {}

