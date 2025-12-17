package com.example.user_authentication.user_authentication_travyotei.dto;

import com.example.user_authentication.user_authentication_travyotei.user_details.Role;
import com.example.user_authentication.user_authentication_travyotei.user_details.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING) 
    private Role role;

    @Enumerated(EnumType.STRING) 
    private Status status;
    
    private String profileImageUrl;
    private long phoneNumber;
    private String address;
    private String licenseNumber;

}
