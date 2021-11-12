package com.nikolasnorth.authservice;

import com.nikolasnorth.authservice.deserialize.GetAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

  private final SessionRepository sessionRepository;

  private final WebClient.Builder client;

  @Autowired
  public AuthService(SessionRepository s, WebClient.Builder w) {
    this.sessionRepository = s;
    this.client = w;
  }

  public void signUp(Account account) {
    System.out.printf("Account %d has signed up!%n", account.getId());
  }

  public Account signIn(Account account) {
    GetAccountResponse res = client.build()
      .get()
      .uri("http://localhost:8082/api/v1/accounts/1")
      .retrieve()
      .bodyToMono(GetAccountResponse.class)
      .block();
    if (res == null) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        String.format("Account with id '%d' does not exist.", account.getId()));
    }
    return res.getAccount();
  }

  public void signOut(Account account) {
    System.out.printf("Account %d has signed out!%n", account.getId());
  }
}
