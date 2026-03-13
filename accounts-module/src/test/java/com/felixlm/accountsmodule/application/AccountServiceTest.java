package com.felixlm.accountsmodule.application;

import com.felixlm.accountsmodule.domain.Account;
import com.felixlm.accountsmodule.domain.AccountRepository;
import com.felixlm.accountsmodule.domain.AccountType;
import com.felixlm.accountsmodule.domain.Transaction;
import com.felixlm.accountsmodule.domain.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceTest {

    private final InMemoryAccountRepository accountRepository = new InMemoryAccountRepository();
    private final InMemoryTransactionRepository transactionRepository = new InMemoryTransactionRepository();
    private final AccountService service = new AccountService(accountRepository, transactionRepository);

    @Test
    void shouldCreateAndDeposit() {
        Account account = service.createAccount(UUID.randomUUID(), AccountType.CHECKING);
        assertThat(accountRepository.accounts).hasSize(1);

        service.deposit(account.getId(), 100);

        assertThat(accountRepository.accounts.getFirst().getBalance().asDouble()).isEqualTo(100.0);
        assertThat(transactionRepository.transactions).hasSize(1);
    }

    private static class InMemoryAccountRepository implements AccountRepository {
        private final List<Account> accounts = new ArrayList<>();

        @Override
        public Account save(Account account) {
            accounts.removeIf(a -> a.getId().equals(account.getId()));
            accounts.add(account);
            return account;
        }

        @Override
        public Optional<Account> findById(UUID id) {
            return accounts.stream().filter(a -> a.getId().equals(id)).findFirst();
        }

        @Override
        public List<Account> findByUserId(UUID userId) {
            return accounts.stream().filter(a -> a.getUserId().equals(userId)).toList();
        }
    }

    private static class InMemoryTransactionRepository implements TransactionRepository {
        private final List<Transaction> transactions = new ArrayList<>();

        @Override
        public Transaction save(Transaction transaction) {
            transactions.add(transaction);
            return transaction;
        }

        @Override
        public List<Transaction> findByAccountId(UUID accountId) {
            return transactions.stream().filter(t -> t.getAccountId().equals(accountId)).toList();
        }
    }
}

