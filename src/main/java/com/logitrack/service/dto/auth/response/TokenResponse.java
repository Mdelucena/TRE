package com.logitrack.service.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private final String token;
    private final String username;
    private final String email;
    private final Long userId;
}
