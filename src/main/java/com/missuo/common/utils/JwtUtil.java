package com.missuo.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  public String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
    // Specify the signature algorithm used when signing, that is, the header part
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // Time to generate jwt
    long expMillis = System.currentTimeMillis() + ttlMillis;
    Date exp = new Date(expMillis);

    // Set the body of jwt
    JwtBuilder builder =
        Jwts.builder()
            .setClaims(claims)
            .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
            .setExpiration(exp);

    return builder.compact();
  }

  public Claims parseJWT(String secretKey, String token) {
    // Get DefaultJwtParser
    return Jwts.parser()
        .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
        .parseClaimsJws(token)
        .getBody();
  }
}
