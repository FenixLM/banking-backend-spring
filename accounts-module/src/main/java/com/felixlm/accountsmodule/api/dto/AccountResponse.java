package com.felixlm.accountsmodule.api.dto;

import com.felixlm.accountsmodule.domain.AccountType;

import java.util.UUID;

public record AccountResponse(
        UUID id,
        UUID userId,
        AccountType type,
        double balance
) {}

