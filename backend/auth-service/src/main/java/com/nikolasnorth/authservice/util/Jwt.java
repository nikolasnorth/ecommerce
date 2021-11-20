package com.nikolasnorth.authservice.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class Jwt implements Serializable {

  private final JwtBuilder jwtBuilder;

  @Value("${jwt.secret}")
  private String secretKey;

  @Autowired
  public Jwt(JwtBuilder j) {
    this.jwtBuilder = j;
  }

  public String generateToken() {
    final Map<String, Object> claims = Map.of();
    return jwtBuilder.setClaims(claims)
      .setSubject("nikolas@uwo.ca")
      .setIssuedAt(Date.from(Instant.now()))
      .setExpiration(Date.from(Instant.now().plusSeconds(300)))  // 5 minutes
      .signWith(SignatureAlgorithm.HS512, secretKey)
      .compact();
  }
}
