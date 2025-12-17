package com.example.user_authentication.user_authentication_travyotei.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_authentication.user_authentication_travyotei.dto.ImageUrlUpdateRequest;
import com.example.user_authentication.user_authentication_travyotei.model.Users;
import com.example.user_authentication.user_authentication_travyotei.service.UsersService;
import com.example.user_authentication.user_authentication_travyotei.user_details.Status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UsersService usersService;

    @PutMapping("/user/profile/image-url") // Use PUT or PATCH for updates
    public ResponseEntity<Users> updateProfileImage(
            @RequestBody ImageUrlUpdateRequest request,
            Principal principal) // Spring Security automatically populates this
    {
        // 'principal.getName()' gives you the authenticated user's email/username
        String userEmail = principal.getName(); 
        
        Users updatedUser = usersService.updateProfileImageUrl(userEmail, request.getNewImageUrl());
        
        return ResponseEntity.ok(updatedUser);
    }

    // 1. Get a particular client 
    @GetMapping("/clients/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENT') and authentication.principal.id == #id)")
    public ResponseEntity<Users> getClientById(@PathVariable UUID id) {
        return usersService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 2. Get a particular agency (Accessible by Agencies viewing their own, or Admin)
    @GetMapping("/agencies/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENCY')")
    public ResponseEntity<Users> getAgencyById(@PathVariable UUID id) {
        return usersService.getAgencyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Get all agencies (Admin only)
    @GetMapping("/agencies")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> getAllAgencies() {
        return usersService.getAllAgencies();
    }

    // 4. Get all agencies with status pending (Admin only)
    @GetMapping("/agencies/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> getPendingAgencies() {
        return usersService.getPendingAgencies();
    }

    // 5. Update an agency's status (Admin only)
    @PutMapping("/agencies/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Users> updateAgencyStatus(@PathVariable UUID id, @RequestParam Status status) {
        Users updatedAgency = usersService.updateAgencyStatus(id, status);
        return ResponseEntity.ok(updatedAgency);
    }
}
