package com.felixlm.accountsmodule.api.dto;

import com.felixlm.accountsmodule.domain.AccountType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAccountRequest(
        @NotNull UUID userId,
        @NotNull AccountType type
) {}

