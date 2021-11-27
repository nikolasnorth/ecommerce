package com.nikolasnorth.authservice.auth;

import com.nikolasnorth.authservice.entities.Account;
import com.nikolasnorth.authservice.entities.AccountCookies;
import com.nikolasnorth.authservice.util.Jwt;
import com.nikolasnorth.aws.SNS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
public class AuthService {

  private final WebClient.Builder client;

  private final Jwt jwt;

  private final AuthRepository authRepository;

  @Autowired
  public AuthService(WebClient.Builder w, Jwt jwt, AuthRepository a) {
    this.client = w;
    this.jwt = jwt;
    this.authRepository = a;
  }

  public AccountCookies signUp(Account account, String password) {
    if (account == null
      || account.getEmail() == null || account.getEmail().isEmpty()
      || account.getName() == null || account.getName().isEmpty()
      || password == null || password.isEmpty()
    ) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields are required.");
    }
    try {
      final Account createdAccount = client.build()
        .post()
        .uri("http://localhost:8082/api/v1/accounts")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(Mono.just(account), Account.class)
        .retrieve()
        .bodyToMono(Account.class)
        .block();
      if (createdAccount == null) {
        System.err.println("Account was unexpectedly found to be null.");
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong on our end.");
      }
      final Auth auth = new Auth(createdAccount.getId(), password);
      authRepository.save(auth);
      
      // Publish account created message to aws topic
   	  String arn = "arn:aws:sns:us-east-2:964806631323:AccountCreated";    // Address of SNS topic to publish to   		
   	  SNS.publishToTopic(arn, createdAccount.getEmail());
      
      return new AccountCookies(
        createAccessTokenCookie(Integer.toString(createdAccount.getId())),
        createRefreshTokenCookie(Integer.toString(createdAccount.getId())),
        createdAccount
      );
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

  public AccountCookies signIn(String email, String password) {
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
      final Auth auth = authRepository.findByAccountId(account.getId()).orElseThrow();
      if (!password.equals(auth.getPassword())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email and/or password did not match.");
      }
      return new AccountCookies(
        createAccessTokenCookie(Integer.toString(account.getId())),
        createRefreshTokenCookie(Integer.toString(account.getId())),
        account
      );
    } catch (WebClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist.");
      } else {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong during sign in.");
      }
    } catch (NoSuchElementException e) {
      System.err.println("Could not find account by id in Auth table.");
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not exist.");
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
