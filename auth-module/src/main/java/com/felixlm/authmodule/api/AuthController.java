package com.felixlm.authmodule.api;

import com.felixlm.authmodule.api.dto.AuthTokenResponse;
import com.felixlm.authmodule.api.dto.LoginRequest;
import com.felixlm.authmodule.api.dto.RegisterRequest;
import com.felixlm.authmodule.application.AuthenticationResponse;
import com.felixlm.authmodule.application.AuthenticationService;
import com.felixlm.authmodule.application.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.jwt.expiration:86400000}")
    private long tokenExpiration;

    public AuthController(AuthenticationService authenticationService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthTokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(
            request.email(),
            request.password(),
            request.fullName()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapToResponse(response));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthenticationResponse response = authenticationService.login(
            request.email(),
            request.password()
        );
        
        return ResponseEntity.ok(mapToResponse(response));
    }

    private AuthTokenResponse mapToResponse(AuthenticationResponse response) {
        return new AuthTokenResponse(
            response.userId(),
            response.email(),
            response.fullName(),
            response.token(),
            "Bearer",
            tokenExpiration / 1000
        );
    }
}

