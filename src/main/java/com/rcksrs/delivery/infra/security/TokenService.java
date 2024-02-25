package com.rcksrs.delivery.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rcksrs.delivery.core.exception.global.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j
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
            log.error("Error generating token. {}", ex.getMessage());
            throw new UnauthorizedException();
        }
    }

    public String getSubject(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(this.secretKey))
                    .withIssuer(this.issuer)
                    .ignoreIssuedAt()
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            log.error("Error validating token. {}", ex.getMessage());
            throw new UnauthorizedException();
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
