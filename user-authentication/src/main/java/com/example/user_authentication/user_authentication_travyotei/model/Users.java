package com.example.user_authentication.user_authentication_travyotei.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;


// Import Spring Security interfaces
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.user_authentication.user_authentication_travyotei.user_details.Role;
import com.example.user_authentication.user_authentication_travyotei.user_details.Status;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Users implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING) 
    private Role role;

    @Enumerated(EnumType.STRING) 
    private Status status;
    
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    @Column(name = "phone_number")
    private long phoneNumber;
    private String address;
    @Column(name = "license_number")
    private String licenseNumber;
    private String bio;
    
    @Column(name = "pricing_plan")
    private String pricingPlan;

    // =============================================
    // CONCRETE ADDITIONS FOR USERDETAILS INTERFACE
    // =============================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's role as a collection of authorities
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // Specify which field serves as the username (for login)
        return this.userName; 
    }
    
    // The following methods assume accounts are always non-expired, non-locked, 
    // and enabled by default. Adjust this logic if you implement features like 
    // account locking or email verification later.

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        // You could check the 'Status' enum here if you wanted to disable accounts
        return true; 
    }
}
