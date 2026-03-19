package com.logitrack.controller;

import com.logitrack.service.AuthService;
import com.logitrack.service.dto.auth.request.CadastroUsuarioRequest;
import com.logitrack.service.dto.auth.request.CredenciaisLoginRequest;
import com.logitrack.service.dto.auth.response.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody CredenciaisLoginRequest loginRequest) {
        TokenResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody CadastroUsuarioRequest registerRequest) {
        TokenResponse response = authService.register(registerRequest);
        return ResponseEntity.status(201).body(response);
    }
}
