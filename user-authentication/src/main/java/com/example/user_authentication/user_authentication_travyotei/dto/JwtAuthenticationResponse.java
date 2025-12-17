package com.example.user_authentication.user_authentication_travyotei.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private String role; // Optional: Send the role back to the frontend
}