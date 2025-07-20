package com.system.SmallBusinessBookingSystem.service.token;

import com.system.SmallBusinessBookingSystem.service.models.User;
import com.system.SmallBusinessBookingSystem.service.models.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final String CLAIM_ROLE = "role";
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.ttl-millis}")
    private Long jwtTtlMillis;

    @Override
    public String createToken(User user) {

        // Calculate the expiration date based on the current time and expiration time in milliseconds
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtTtlMillis);

        // Build JWT claims
        Claims claims = Jwts.claims()
                .issuedAt(now)
                .expiration(expiration)
                .subject(user.getId())
                .add(CLAIM_ROLE, user.getType().toString())
                .build();


        // Create and sign the JWT token
        return Jwts.builder()
                .claims(claims)
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {

        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public String getUserId(String token) {

        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public UserType getUserType(String token) {

        String typeValue = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(CLAIM_ROLE, String.class);

        return UserType.valueOf(typeValue);
    }

    private SecretKey getSecretKey() {

        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}