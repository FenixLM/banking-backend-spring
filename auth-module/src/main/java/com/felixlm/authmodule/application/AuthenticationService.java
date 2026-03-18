package com.felixlm.authmodule.application;

import com.felixlm.authmodule.domain.AuthenticationException;
import com.felixlm.usersmodule.domain.User;
import com.felixlm.usersmodule.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public AuthenticationResponse register(String email, String password, String fullName) {
        if (userRepository.existsByEmail(email)) {
            throw new AuthenticationException("Email already registered");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.create(fullName, email, encodedPassword);
        User savedUser = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(savedUser.getId(), savedUser.getEmail());
        return new AuthenticationResponse(
            savedUser.getId(),
            savedUser.getEmail(),
            savedUser.getName(),
            token
        );
    }

    @Transactional
    public AuthenticationResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!user.isEnabled()) {
            throw new AuthenticationException("Account is disabled");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());
        return new AuthenticationResponse(
            user.getId(),
            user.getEmail(),
            user.getName(),
            token
        );
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new AuthenticationException("User not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new AuthenticationException("User not found"));
    }
}

