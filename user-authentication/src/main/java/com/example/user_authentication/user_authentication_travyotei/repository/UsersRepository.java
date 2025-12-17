package com.example.user_authentication.user_authentication_travyotei.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user_authentication.user_authentication_travyotei.model.Users;
import com.example.user_authentication.user_authentication_travyotei.user_details.Role;
import com.example.user_authentication.user_authentication_travyotei.user_details.Status;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    
    Optional<Users> findByUserName(String userName);
    Optional<Users> findByEmail(String email);

    // Get a particular Client (find by ID and check role)
    Optional<Users> findByIdAndRole(UUID id, Role role);

     // Get all Agencies (find all by Role)
    List<Users> findByRole(Role role);

    // Get all Agencies with PENDING status (find all by Role and Status)
    List<Users> findByRoleAndStatus(Role role, Status status);
    
}
