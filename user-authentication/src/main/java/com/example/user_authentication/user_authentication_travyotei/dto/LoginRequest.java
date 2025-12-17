package com.example.user_authentication.user_authentication_travyotei.dto;

import lombok.Data; // Or use simple getters/setters

@Data
public class LoginRequest {
    private String userName;    // Matches your UserEntity's email/username
    private String password;
}