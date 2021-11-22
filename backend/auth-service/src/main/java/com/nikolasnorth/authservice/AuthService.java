package com.nikolasnorth.authservice;

import com.nikolasnorth.authservice.util.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.HashMap;

@Service
public class AuthService {

  private final WebClient.Builder client;

  private final Jwt jwt;

  @Autowired
  public AuthService(WebClient.Builder w, Jwt jwt) {
    this.client = w;
    this.jwt = jwt;
  }

  public Account signUp(Account account, String password) {
    if (account == null
      || account.getEmail() == null || account.getEmail().isEmpty()
      || account.getName() == null || account.getName().isEmpty()
      || password == null || password.isEmpty()
    ) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required.");
    }
    try {
      final Account created = client.build()
        .post()
        .uri("http://localhost:8082/api/v1/accounts")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(Mono.just(account), Account.class)
        .retrieve()
        .bodyToMono(Account.class)
        .block();
      if (created == null) {
        System.err.println("Account was unexpectedly found to be null.");
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong on our end.");
      }
      return created;
    } catch (WebClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist.");
      } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      } else {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  }

  public Account signIn(String email, String password) {
    if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required.");
    }
    try {
      final Account account = client.build()
        .get()
        .uri(String.format("http://localhost:8082/api/v1/accounts?email=%s", email))
        .retrieve()
        .bodyToMono(Account.class)
        .block();
      if (account == null) {
        System.err.println("Account was unexpectedly found to be null.");
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong on our end.");
      }
      return account;
    } catch (WebClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist.");
      } else {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong during sign in.");
      }
    }
  }

  public void signOut(Account account) {
    System.out.printf("Account %d has signed out!%n", account.getId());
  }

  public Cookie createAccessTokenCookie(String subject) {
    final var cookie
      = new Cookie("access-token", jwt.generateToken(new HashMap<>(), subject, 300));  // expires in 5 minutes
    cookie.setHttpOnly(true);
    return cookie;
  }

  public Cookie createRefreshTokenCookie(String subject) {
    final var cookie
      = new Cookie("refresh-token", jwt.generateToken(new HashMap<>(), subject, 31_536_000));  // expires in 1 year
    cookie.setHttpOnly(true);
    return cookie;
  }
}
