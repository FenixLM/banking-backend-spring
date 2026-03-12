package com.felixlm.usersmodule.api.dto;

public record CreateUserRequest(
        String name,
        String email
) {}
