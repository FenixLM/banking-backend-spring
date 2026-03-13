package com.felixlm.accountsmodule.application;

import com.felixlm.accountsmodule.domain.Account;
import com.felixlm.accountsmodule.domain.AccountRepository;
import com.felixlm.accountsmodule.domain.AccountType;
import com.felixlm.accountsmodule.domain.Money;
import com.felixlm.accountsmodule.domain.Transaction;
import com.felixlm.accountsmodule.domain.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account createAccount(UUID userId, AccountType type) {
        Account account = Account.open(userId, type);
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(UUID accountId, double amount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Money money = Money.of(amount);
        account.deposit(money);
        accountRepository.save(account);
        transactionRepository.save(Transaction.create(accountId, money, "Deposit"));
        return account;
    }

    @Transactional
    public Account withdraw(UUID accountId, double amount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Money money = Money.of(amount);
        account.withdraw(money);
        accountRepository.save(account);
        transactionRepository.save(Transaction.create(accountId, money, "Withdrawal"));
        return account;
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactions(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}

