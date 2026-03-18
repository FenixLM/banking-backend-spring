package com.felixlm.authmodule.application;

import com.felixlm.authmodule.domain.AuthenticationException;
import com.felixlm.authmodule.domain.User;
import com.felixlm.authmodule.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthenticationService authenticationService;

    private String testEmail;
    private String testPassword;
    private String testFullName;

    @BeforeEach
    void setUp() {
        testEmail = "test@example.com";
        testPassword = "password123";
        testFullName = "Test User";
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        when(userRepository.existsByEmail(testEmail)).thenReturn(false);
        when(passwordEncoder.encode(testPassword)).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtTokenProvider.generateToken(any(UUID.class), anyString())).thenReturn("token");

        AuthenticationResponse response = authenticationService.register(testEmail, testPassword, testFullName);

        assertNotNull(response);
        assertEquals(testEmail, response.email());
        assertEquals(testFullName, response.fullName());
        assertEquals("token", response.token());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyRegistered() {
        when(userRepository.existsByEmail(testEmail)).thenReturn(true);

        assertThrows(AuthenticationException.class, () ->
            authenticationService.register(testEmail, testPassword, testFullName)
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldLoginSuccessfully() {
        UUID userId = UUID.randomUUID();
        User user = User.create(testEmail, "hashedPassword", testFullName);
        
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(testPassword, user.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(any(UUID.class), anyString())).thenReturn("token");

        AuthenticationResponse response = authenticationService.login(testEmail, testPassword);

        assertNotNull(response);
        assertEquals(testEmail, response.email());
        assertEquals("token", response.token());
    }

    @Test
    void shouldThrowExceptionWhenLoginWithInvalidEmail() {
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () ->
            authenticationService.login(testEmail, testPassword)
        );
    }

    @Test
    void shouldThrowExceptionWhenLoginWithWrongPassword() {
        User user = User.create(testEmail, "hashedPassword", testFullName);
        
        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(testPassword, user.getPassword())).thenReturn(false);

        assertThrows(AuthenticationException.class, () ->
            authenticationService.login(testEmail, testPassword)
        );
    }

    @Test
    void shouldGetUserById() {
        UUID userId = UUID.randomUUID();
        User user = User.create(testEmail, "hashedPassword", testFullName);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = authenticationService.getUserById(userId);

        assertNotNull(result);
        assertEquals(testEmail, result.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () ->
            authenticationService.getUserById(userId)
        );
    }
}

