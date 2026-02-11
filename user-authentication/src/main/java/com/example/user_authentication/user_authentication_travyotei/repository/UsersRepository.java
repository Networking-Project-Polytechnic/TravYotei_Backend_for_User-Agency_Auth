package com.example.user_authentication.user_authentication_travyotei.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_authentication.user_authentication_travyotei.model.Users;
import com.example.user_authentication.user_authentication_travyotei.user_details.Role;
import com.example.user_authentication.user_authentication_travyotei.user_details.Status;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    
    Optional<Users> findByUserName(String userName);
    Optional<Users> findByEmail(String email);

    Optional<Users> findByIdAndRole(UUID id, Role role);

    List<Users> findByRole(Role role);

    List<Users> findByRoleAndStatus(Role role, Status status);

    // Bulk update: set createdAt = :now where createdAt is null
    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.createdAt = :now WHERE u.createdAt IS NULL")
    int setCreatedAtWhereNull(@Param("now") Instant now);
}
