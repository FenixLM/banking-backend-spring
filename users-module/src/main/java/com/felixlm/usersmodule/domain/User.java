package com.felixlm.usersmodule.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String name;
    private final String email;
    private final String password;
    private final Set<Role> roles;
    private final boolean enabled;

    private User(UUID id, String name, String email, String password, Set<Role> roles, boolean enabled) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.name = name == null ? "" : name.trim();
        this.email = email == null ? "" : email.trim().toLowerCase();
        this.password = Objects.requireNonNull(password, "password is required");
        this.roles = roles != null ? roles : new HashSet<>();
        this.enabled = enabled;
        
        if (this.name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (this.email.isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
    }

    public static User create(String name, String email, String password) {
        return new User(UUID.randomUUID(), name, email, password, new HashSet<>(), true);
    }

    public static User reconstruct(UUID id, String name, String email, String password, Set<Role> roles, boolean enabled) {
        return new User(id, name, email, password, roles, enabled);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
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

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return new HashSet<>(roles);
    }

    public boolean isEnabled() {
        return enabled;
    }
}

