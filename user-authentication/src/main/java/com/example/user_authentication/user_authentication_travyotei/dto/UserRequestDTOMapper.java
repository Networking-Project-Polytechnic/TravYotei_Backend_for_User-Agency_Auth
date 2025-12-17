package com.example.user_authentication.user_authentication_travyotei.dto;

import com.example.user_authentication.user_authentication_travyotei.model.Users;

public class UserRequestDTOMapper {

    public Users toEntity(UserRequestDTO dto) {
        Users user = new Users();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        // user.setRole(dto.getRole());   Opted for setting role separately based on context
        // user.setStatus(dto.getStatus()); Opted for setting status separately based on context
        // user.setProfileImageUrl(dto.getProfileImageUrl()); // Will make the setting of profile images just from the dashboard
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setLicenseNumber(dto.getLicenseNumber());
        return user;
    }
}
