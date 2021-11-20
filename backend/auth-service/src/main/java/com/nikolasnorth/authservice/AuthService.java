package com.nikolasnorth.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final WebClient.Builder client;

  @Autowired
  public AuthService(WebClient.Builder w) {
    this.client = w;
  }

  public void signUp(Account account) {
    System.out.printf("Account %d has signed up!%n", account.getId());
  }

  public Account signIn(String email, String password) {
    try {
      if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required.");
      }
      Account account = client.build()
        .get()
        .uri(String.format("http://localhost:8082/api/v1/accounts?email=%s", email))
        .retrieve()
        .bodyToMono(Account.class)
        .block();
      if (account == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist.");
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
}
