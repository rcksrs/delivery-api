package com.rcksrs.delivery.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${app.jwt.secret-key}")
    private String secretKey;

    @Value("${app.jwt.issuer}")
    private String issuer;

    @Value("${app.jwt.expiration-hours}")
    private Long expiration;

    public String generate(UserDetailsEntity user) {
        try {
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(getExpiresAt())
                    .withIssuedAt(getIssuedAt())
                    .withIssuer(this.issuer)
                    .sign(Algorithm.HMAC256(this.secretKey));

        } catch (JWTCreationException ex) {
            throw new JWTCreationException("Error generating token", ex);
        }
    }

    public String getSubject(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(this.secretKey))
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException("Invalid token");
        }
    }

    private Instant getIssuedAt() {
        return LocalDateTime.now()
                .toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant getExpiresAt() {
        return LocalDateTime.now()
                .plusHours(this.expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
