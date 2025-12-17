package com.example.user_authentication.user_authentication_travyotei.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.user_authentication.user_authentication_travyotei.model.Users;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret:YourReallyStrongSecretKeyForJWTAuthenticationWhichShouldBeAtLeast256BitsLong}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}")
    private long jwtExpirationDateInMillis;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication) {
        Users userPrincipal = (Users) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationDateInMillis);

        return Jwts.builder()
                // Use the new builder-style methods: subject(), issuedAt(), expiration()
                .subject(userPrincipal.getUsername()) //
                .issuedAt(now) //
                .expiration(expiryDate) //
                // Use the non-deprecated signWith(Key key) method
                .signWith(getSigningKey()) 
                .compact();
    }
}