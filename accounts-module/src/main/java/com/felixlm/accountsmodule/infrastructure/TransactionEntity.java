package com.felixlm.accountsmodule.infrastructure;

import com.felixlm.accountsmodule.domain.Money;
import com.felixlm.accountsmodule.domain.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID accountId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Column(nullable = false)
    private String description;

    protected TransactionEntity() {
    }

    private TransactionEntity(UUID id, UUID accountId, BigDecimal amount, LocalDateTime occurredAt, String description) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.occurredAt = occurredAt;
        this.description = description;
    }

    public static TransactionEntity fromDomain(Transaction transaction) {
        return new TransactionEntity(
            transaction.getId(),
            transaction.getAccountId(),
            new BigDecimal(transaction.getAmount().asDouble()),
            transaction.getOccurredAt(),
            transaction.getDescription()
        );
    }

    public Transaction toDomain() {
        return Transaction.reconstruct(id, accountId, Money.of(amount.doubleValue()), occurredAt, description);
    }

    public UUID getAccountId() {
        return accountId;
    }
}

