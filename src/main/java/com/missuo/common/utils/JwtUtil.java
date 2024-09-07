package com.missuo.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  public String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {

    // Convert the secret key string into a SecretKey object using the Keys class
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // Time to generate jwt
    Instant now = Instant.now();
    Instant expiration = now.plus(ttlMillis, ChronoUnit.MILLIS);

    // Set the body of jwt
    return Jwts.builder()
        .claims(claims)
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiration))
        .signWith(key)
        .compact();
  }

  public Claims parseJWT(String secretKey, String token) {
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // Get DefaultJwtParser
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }
}
