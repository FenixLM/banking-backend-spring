package com.felixlm.usersmodule.domain;

import java.util.Objects;

public class Role {

    private final Long id;
    private final String name;

    private Role(Long id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name is required");
    }

    public static Role create(String name) {
        return new Role(null, name);
    }

    public static Role reconstruct(Long id, String name) {
        return new Role(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

