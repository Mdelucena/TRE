package com.logitrack.service;

import com.logitrack.domain.User;
import com.logitrack.repository.UserRepository;
import com.logitrack.security.JwtTokenProvider;
import com.logitrack.service.dto.auth.request.CredenciaisLoginRequest;
import com.logitrack.service.dto.auth.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginAndReturnTokenWhenCredentialsAreValid() {
        CredenciaisLoginRequest request = new CredenciaisLoginRequest("admin", "admin123");
        User user = new User("admin", "admin@logitrack.com", "encoded");
        user.setId(1L);

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("admin123", "encoded")).thenReturn(true);
        when(jwtTokenProvider.generateToken("admin")).thenReturn("jwt-token");

        TokenResponse response = authService.login(request);

        assertEquals("jwt-token", response.getToken());
        assertEquals("admin", response.getUsername());
        assertEquals("admin@logitrack.com", response.getEmail());
        assertEquals(1L, response.getUserId());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        CredenciaisLoginRequest request = new CredenciaisLoginRequest("admin", "wrong");
        User user = new User("admin", "admin@logitrack.com", "encoded");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> authService.login(request));
    }
}
