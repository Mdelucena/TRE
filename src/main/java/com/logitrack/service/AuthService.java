package com.logitrack.service;

import com.logitrack.domain.User;
import com.logitrack.repository.UserRepository;
import com.logitrack.security.JwtTokenProvider;
import com.logitrack.service.dto.auth.request.CadastroUsuarioRequest;
import com.logitrack.service.dto.auth.request.CredenciaisLoginRequest;
import com.logitrack.service.dto.auth.response.TokenResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(CredenciaisLoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario ou senha inválidos"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Usuario ou senha inválidos");
        }

        String token = jwtTokenProvider.generateToken(user.getUsername());
        return new TokenResponse(token, user.getUsername(), user.getEmail(), user.getId());
    }

    public TokenResponse register(CadastroUsuarioRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username já existe");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email já existe");
        }

        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword())
        );

        userRepository.save(user);
        String token = jwtTokenProvider.generateToken(user.getUsername());
        return new TokenResponse(token, user.getUsername(), user.getEmail(), user.getId());
    }
}
