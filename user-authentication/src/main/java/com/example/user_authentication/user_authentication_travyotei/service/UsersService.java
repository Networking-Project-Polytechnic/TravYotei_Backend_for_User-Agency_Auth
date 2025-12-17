package com.example.user_authentication.user_authentication_travyotei.service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_authentication.user_authentication_travyotei.dto.UserRequestDTO;
import com.example.user_authentication.user_authentication_travyotei.model.Users;
import com.example.user_authentication.user_authentication_travyotei.repository.UsersRepository;
import com.example.user_authentication.user_authentication_travyotei.user_details.Role;
import com.example.user_authentication.user_authentication_travyotei.user_details.Status;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService{

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<Users> user =  usersRepository.findByUserName(username);
        if(user.isPresent()){
            var userObj = user.get();
            return User.builder()
                .username(userObj.getUsername())
                .password(userObj.getPassword())
                .roles(userObj.getRole().name())
                .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    public Users createClient(UserRequestDTO clientDTO) {

        if (usersRepository.findByEmail(clientDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        if (usersRepository.findByUserName(clientDTO.getUserName()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        Users client = new Users();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setUserName(clientDTO.getUserName());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword())); 
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setAddress(clientDTO.getAddress());
        client.setRole(Role.ROLE_CLIENT);

        return usersRepository.save(client);
    }

    public Users createAgency(UserRequestDTO agencyDTO) {

        if (usersRepository.findByEmail(agencyDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        if (usersRepository.findByUserName(agencyDTO.getUserName()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        Users agency = new Users();
        agency.setFirstName(agencyDTO.getFirstName());
        agency.setLastName(agencyDTO.getLastName());
        agency.setUserName(agencyDTO.getUserName());
        agency.setEmail(agencyDTO.getEmail());
        agency.setPassword(passwordEncoder.encode(agencyDTO.getPassword())); 
        agency.setPhoneNumber(agencyDTO.getPhoneNumber());
        agency.setAddress(agencyDTO.getAddress());
        agency.setLicenseNumber(agencyDTO.getLicenseNumber());
        agency.setRole(Role.ROLE_AGENCY);
        agency.setStatus(Status.PENDING);
        
        return usersRepository.save(agency);
    }


    @Transactional // Ensures the update happens within a database transaction
    public Users updateProfileImageUrl(String userEmail, String newImageUrl) {
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setProfileImageUrl(newImageUrl);
        // The save is often implicit due to @Transactional and JPA's persistence context, 
        // but calling save() explicitly is good practice:
        return usersRepository.save(user); 
    }

    // Get a particular client
    public Optional<Users> getClientById(UUID id) {
        return usersRepository.findByIdAndRole(id, Role.ROLE_CLIENT);
    }

    // Get a particular agency
    public Optional<Users> getAgencyById(UUID id) {
        return usersRepository.findByIdAndRole(id, Role.ROLE_AGENCY);
    }

    // Get all agencies
    public List<Users> getAllAgencies() {
        return usersRepository.findByRole(Role.ROLE_AGENCY);
    }

    // Get all pending agencies
    public List<Users> getPendingAgencies() {
        return usersRepository.findByRoleAndStatus(Role.ROLE_AGENCY, Status.PENDING);
    }
    
    // Update an agency's status (for use by an Admin)
    public Users updateAgencyStatus(UUID agencyId, Status newStatus) {
        Users agency = getAgencyById(agencyId)
                .orElseThrow(() -> new RuntimeException("Agency not found or not an agency"));
                
        agency.setStatus(newStatus);

        return usersRepository.save(agency);
    }
}
