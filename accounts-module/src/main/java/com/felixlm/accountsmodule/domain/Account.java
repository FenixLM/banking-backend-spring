package com.felixlm.accountsmodule.domain;

import java.util.UUID;

public class Account {

    private final UUID id;
    private final UUID userId;
    private final AccountType type;
    private Money balance;

    private Account(UUID id, UUID userId, AccountType type, Money balance) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.balance = balance;
    }

    public static Account open(UUID userId, AccountType type) {
        return new Account(UUID.randomUUID(), userId, type, Money.zero());
    }

    public static Account reconstruct(UUID id, UUID userId, AccountType type, Money balance) {
        return new Account(id, userId, type, balance);
    }

    public void deposit(Money amount) {
        if (amount == null || amount.isLessThan(Money.zero())) {
            throw new BusinessException("Deposit amount must be positive");
        }
        balance = balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (amount == null || amount.isLessThan(Money.zero())) {
            throw new BusinessException("Withdrawal amount must be positive");
        }
        if (balance.isLessThan(amount)) {
            throw new BusinessException("Insufficient funds");
        }
        balance = balance.subtract(amount);
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

    public Money getBalance() {
        return balance;
    }
}

