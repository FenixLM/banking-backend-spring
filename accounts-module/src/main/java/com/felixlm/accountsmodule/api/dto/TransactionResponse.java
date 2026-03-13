package com.felixlm.accountsmodule.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID accountId,
        double amount,
        LocalDateTime occurredAt,
        String description
) {}

