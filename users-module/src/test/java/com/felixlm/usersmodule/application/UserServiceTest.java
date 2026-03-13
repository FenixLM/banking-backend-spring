package com.felixlm.usersmodule.application;

import com.felixlm.usersmodule.domain.User;
import com.felixlm.usersmodule.domain.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private final InMemoryUserRepository repository = new InMemoryUserRepository();
    private final UserService userService = new UserService(repository);

    @Test
    void shouldCreateUser() {
        User created = userService.createUser("Alice", "alice@example.com");

        assertThat(created.getId()).isNotNull();
        assertThat(repository.findAll()).hasSize(1);
    }

    private static class InMemoryUserRepository implements UserRepository {
        private final List<User> storage = new ArrayList<>();

        @Override
        public User save(User user) {
            storage.add(user);
            return user;
        }

        @Override
        public List<User> findAll() {
            return storage;
        }

        @Override
        public boolean existsByEmail(String email) {
            return storage.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
        }
    }
}

