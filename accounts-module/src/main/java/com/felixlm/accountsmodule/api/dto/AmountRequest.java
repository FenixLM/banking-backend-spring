package com.felixlm.accountsmodule.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record AmountRequest(
        @NotNull
        @DecimalMin(value = "0.01", message = "amount must be positive")
        Double amount
) {}

