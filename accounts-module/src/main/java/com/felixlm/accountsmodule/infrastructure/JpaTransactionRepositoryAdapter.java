package com.felixlm.accountsmodule.infrastructure;

import com.felixlm.accountsmodule.domain.Transaction;
import com.felixlm.accountsmodule.domain.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaTransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionJpaRepository repository;

    public JpaTransactionRepositoryAdapter(TransactionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = TransactionEntity.fromDomain(transaction);
        return repository.save(entity).toDomain();
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        return repository.findByAccountId(accountId).stream()
            .map(TransactionEntity::toDomain)
            .toList();
    }
}

