package com.felixlm.usersmodule.infrastructure;

import com.felixlm.usersmodule.domain.User;
import com.felixlm.usersmodule.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepository implements UserRepository {

    private final UserJpaRepository repository;

    public JpaUserRepository(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        return repository.save(entity).toDomain();
    }

    @Override
    public List<User> findAll() {
        return repository.findAll().stream()
            .map(UserEntity::toDomain)
            .toList();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}



