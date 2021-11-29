package com.nikolasnorth.apigateway.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Objects;

@Component
public class JwtFilterFactory extends AbstractGatewayFilterFactory<JwtFilterFactory.Config> {

  private final byte[] jwtSecret;

  public JwtFilterFactory(@Value("${jwt.secret}") String jwtSecret) {
    this.jwtSecret = Base64.getDecoder().decode(jwtSecret);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      final String accessTokenString = exchange.getRequest().getHeaders().toSingleValueMap().get("Authorization");
      if (accessTokenString == null) {
        // No access token received
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      final String[] splitAccessToken = accessTokenString.split("\\s+");  // split at any whitespace
      if (splitAccessToken.length != 2 || !Objects.equals(splitAccessToken[0], "Bearer")) {
        // Invalid format
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      final Jws<Claims> accessJwt = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret))
        .build()
        .parseClaimsJws(splitAccessToken[1]);

      return chain.filter(exchange);
    });
  }

  public static class Config {
  }
}
