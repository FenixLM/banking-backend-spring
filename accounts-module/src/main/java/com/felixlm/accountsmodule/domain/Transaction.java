package com.felixlm.accountsmodule.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private final UUID id;
    private final UUID accountId;
    private final Money amount;
    private final LocalDateTime occurredAt;
    private final String description;

    private Transaction(UUID id, UUID accountId, Money amount, LocalDateTime occurredAt, String description) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.occurredAt = occurredAt;
        this.description = description;
    }

    public static Transaction create(UUID accountId, Money amount, String description) {
        return new Transaction(UUID.randomUUID(), accountId, amount, LocalDateTime.now(), description);
    }

    public static Transaction reconstruct(UUID id, UUID accountId, Money amount, LocalDateTime occurredAt, String description) {
        return new Transaction(id, accountId, amount, occurredAt, description);
    }

    public UUID getId() {
        return id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public String getDescription() {
        return description;
    }
}

