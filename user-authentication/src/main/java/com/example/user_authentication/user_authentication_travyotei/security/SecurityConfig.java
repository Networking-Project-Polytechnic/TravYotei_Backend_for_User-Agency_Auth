package com.example.user_authentication.user_authentication_travyotei.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

// You need to create this filter class yourself. 
// It will intercept requests and validate the JWT token from the Authorization header.
// private final JwtAuthenticationFilter jwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Enables @PreAuthorize on controller methods
@RequiredArgsConstructor // Automatically injects final fields (usersService and jwtAuthenticationFilter if you add it)
public class SecurityConfig {

    @Qualifier("customUserDetailsService")
    private final UserDetailsService userDetailsService;
    // private final JwtAuthenticationFilter jwtAuthenticationFilter; // Uncomment this line and create the filter class

    @Bean
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService); 
        authProvider.setPasswordEncoder(passwordEncoder()); 
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless JWT APIs
                .formLogin(form -> form.disable()) // Disable default form login
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use JWTs, no sessions

                .authorizeHttpRequests(auth -> {
                    // Public Endpoints (accessible without a token)
                    auth.requestMatchers("/api/v1/auth/login", 
                                         "/api/v1/auth/client/register", 
                                         "/api/v1/auth/agency/register").permitAll()
                        // All other endpoints require a valid token (authentication)
                        .anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Configure which frontend origins are allowed to access your API
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://yourproductiondomain.com")); 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }
}
