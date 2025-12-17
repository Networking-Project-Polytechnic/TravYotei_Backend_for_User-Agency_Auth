package com.example.user_authentication.user_authentication_travyotei.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_authentication.user_authentication_travyotei.dto.JwtAuthenticationResponse;
import com.example.user_authentication.user_authentication_travyotei.dto.LoginRequest;
import com.example.user_authentication.user_authentication_travyotei.model.Users;
import com.example.user_authentication.user_authentication_travyotei.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Use the AuthenticationManager to verify credentials
        // This process calls your CustomUserDetailsService and PasswordEncoder internally
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUserName(),
                loginRequest.getPassword()
            )
        );

        // If authentication is successful, set the security context (optional if using pure JWT)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the JWT token for the client to use in future requests
        String jwt = tokenProvider.generateToken(authentication);
        
        // Extract the role to send back to the frontend
        Users userDetails = (Users) authentication.getPrincipal();
        String userRole = userDetails.getAuthorities().iterator().next().getAuthority();

        // Return the token and role in the response body
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userRole));
    }
}
