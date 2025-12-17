package com.example.user_authentication.user_authentication_travyotei.service;



import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.user_authentication.user_authentication_travyotei.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Primary
@Service("customUserDetailsService") // Give it a specific name
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // Use your existing logic here
        return usersRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
    }
}
