package com.missuo.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  public String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
    // Specify the signature algorithm used when signing, that is, the header part
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // Convert the secret key string into a SecretKey object using the Keys class
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // Time to generate jwt
    long expMillis = System.currentTimeMillis() + ttlMillis;
    Date exp = new Date(expMillis);

    // Set the body of jwt
    JwtBuilder builder =
        Jwts.builder().setClaims(claims).signWith(key, signatureAlgorithm).setExpiration(exp);

    return builder.compact();
  }

  public Claims parseJWT(String secretKey, String token) {
    // Get DefaultJwtParser
    return Jwts.parserBuilder()
        .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
