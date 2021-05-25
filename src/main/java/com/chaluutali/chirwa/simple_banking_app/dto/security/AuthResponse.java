package com.chaluutali.chirwa.simple_banking_app.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthResponse {
    @NonNull
    private String jwt;
}
