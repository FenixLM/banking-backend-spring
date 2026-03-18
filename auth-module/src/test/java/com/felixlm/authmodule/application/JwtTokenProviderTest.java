package com.felixlm.authmodule.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider tokenProvider;

    private String secret = "mySecretKeyForJwtTokenGenerationAndVerificationPurposeOnly";
    private long expiration = 86400000;

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(tokenProvider, "secret", secret);
        ReflectionTestUtils.setField(tokenProvider, "expiration", expiration);
    }

    @Test
    void shouldGenerateTokenSuccessfully() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";

        String token = tokenProvider.generateToken(userId, email);

        assertNotNull(token);
        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void shouldExtractUserIdFromToken() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";

        String token = tokenProvider.generateToken(userId, email);
        UUID extractedUserId = tokenProvider.getUserIdFromToken(token);

        assertEquals(userId, extractedUserId);
    }

    @Test
    void shouldExtractEmailFromToken() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";

        String token = tokenProvider.generateToken(userId, email);
        String extractedEmail = tokenProvider.getEmailFromToken(token);

        assertEquals(email, extractedEmail);
    }

    @Test
    void shouldValidateTokenSuccessfully() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";

        String token = tokenProvider.generateToken(userId, email);

        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void shouldFailValidationWithInvalidToken() {
        assertFalse(tokenProvider.validateToken("invalid.token.here"));
    }
}
