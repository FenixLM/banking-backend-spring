package com.felixlm.usersmodule.application;

import com.felixlm.usersmodule.domain.User;
import com.felixlm.usersmodule.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // User creation is handled by AuthenticationService in auth-module
    // to ensure password encoding and security validation

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}

