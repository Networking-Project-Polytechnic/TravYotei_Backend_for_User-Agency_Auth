package com.example.user_authentication.user_authentication_travyotei.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_authentication.user_authentication_travyotei.dto.UserRequestDTO;
import com.example.user_authentication.user_authentication_travyotei.service.UsersService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth/agency")
public class AgencyController {

    private final UsersService usersService;
    

    @PostMapping("/register")
    public ResponseEntity<?> registerAgency(@RequestBody UserRequestDTO request){

        try {

            usersService.createAgency(request);
            return new ResponseEntity<>("Agency registered successfully", HttpStatus.CREATED);
       
        } catch (RuntimeException e) {
            
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
