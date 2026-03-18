package com.felixlm.usersmodule.infrastructure;

import com.felixlm.usersmodule.domain.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public RoleEntity() {
    }

    public RoleEntity(String name) {
        this.name = name;
    }

    public static RoleEntity fromDomain(Role role) {
        return new RoleEntity(role.getName());
    }

    public Role toDomain() {
        return Role.reconstruct(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

