package com.felixlm.usersmodule.domain;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String name;
    private final String email;

    private User(UUID id, String name, String email) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.name = name == null ? "" : name.trim();
        this.email = email == null ? "" : email.trim().toLowerCase();
        if (this.name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (this.email.isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
    }

    public static User create(String name, String email) {
        return new User(UUID.randomUUID(), name, email);
    }

    public static User reconstruct(UUID id, String name, String email) {
        return new User(id, name, email);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

