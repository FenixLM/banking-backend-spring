package com.felixlm.accountsmodule.infrastructure;

import com.felixlm.accountsmodule.domain.Account;
import com.felixlm.accountsmodule.domain.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaAccountRepositoryAdapter implements AccountRepository {

    private final AccountJpaRepository repository;

    public JpaAccountRepositoryAdapter(AccountJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = AccountEntity.fromDomain(account);
        return repository.save(entity).toDomain();
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return repository.findById(id).map(AccountEntity::toDomain);
    }

    @Override
    public List<Account> findByUserId(UUID userId) {
        return repository.findByUserId(userId).stream()
            .map(AccountEntity::toDomain)
            .toList();
    }
}

