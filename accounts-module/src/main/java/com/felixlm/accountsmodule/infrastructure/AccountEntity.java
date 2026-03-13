package com.felixlm.accountsmodule.infrastructure;

import com.felixlm.accountsmodule.domain.Account;
import com.felixlm.accountsmodule.domain.AccountType;
import com.felixlm.accountsmodule.domain.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    protected AccountEntity() {
    }

    private AccountEntity(UUID id, UUID userId, AccountType type, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.balance = balance;
    }

    public static AccountEntity fromDomain(Account account) {
        return new AccountEntity(
            account.getId(),
            account.getUserId(),
            account.getType(),
            new BigDecimal(account.getBalance().asDouble())
        );
    }

    public Account toDomain() {
        return Account.reconstruct(id, userId, type, Money.of(balance.doubleValue()));
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public AccountType getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}

