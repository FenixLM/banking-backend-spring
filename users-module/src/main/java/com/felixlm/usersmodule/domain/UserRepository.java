package com.felixlm.usersmodule.domain;

import java.util.List;

public interface UserRepository {
    void save(User user);
    List<User> findAll();
}
