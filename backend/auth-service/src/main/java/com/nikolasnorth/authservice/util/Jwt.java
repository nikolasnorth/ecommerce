package com.nikolasnorth.authservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class Jwt {

  private final byte[] secretKey;

  @Autowired
  public Jwt(@Value("${jwt.secret}") String secretKey) {
    this.secretKey = Base64.getDecoder().decode(secretKey);
  }

  public String generateToken(Map<String, Object> claims, String sub, int expiresInSeconds) {
    final Instant now = Instant.now();
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(sub)
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(now.plusSeconds(expiresInSeconds)))
      .signWith(Keys.hmacShaKeyFor(secretKey))
      .compact();
  }
}
