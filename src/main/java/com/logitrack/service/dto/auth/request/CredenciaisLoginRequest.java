package com.logitrack.service.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredenciaisLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
