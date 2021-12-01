package com.nikolasnorth.authservice.auth;

import com.nikolasnorth.authservice.aws.SnsService;
import com.nikolasnorth.authservice.entities.Account;
import com.nikolasnorth.authservice.entities.ServiceRegistryResponseBody;
import com.nikolasnorth.authservice.util.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Service
public class AuthService {

  private final WebClient.Builder client;

  private final Jwt jwt;

  private final AuthRepository authRepository;

  private final SnsService snsService;

  private final RestTemplate restTemplate;

  private final String arn;

  private final String serviceRegistryUrl;

  @Autowired
  public AuthService(
    WebClient.Builder webClientBuilder,
    Jwt jwt,
    AuthRepository authRepository,
    SnsService snsService,
    RestTemplate restTemplate,
    @Value("${sns.topic.arn}") String arn,
    @Value("${serviceRegistry.url}") String serviceRegistryUrl
  ) {
    this.client = webClientBuilder;
    this.jwt = jwt;
    this.authRepository = authRepository;
    this.snsService = snsService;
    this.restTemplate = restTemplate;
    this.arn = arn;
    this.serviceRegistryUrl = serviceRegistryUrl;
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
      final var res
        = restTemplate.getForEntity(String.format("%s/%d", serviceRegistryUrl, 2), ServiceRegistryResponseBody.class);
      if (res.getStatusCode() != HttpStatus.OK) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
      }
      ServiceRegistryResponseBody body = res.getBody();
      if (body == null || body.getServiceLocation() == null) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
      }
      final Account createdAccount = client.build()
        .post()
        .uri(body.getServiceLocation() + "/api/v1/accounts")
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
   	  snsService.publishToTopic(arn, createdAccount.getEmail());
      snsService.publishToTopic(arn, "{\"service\": \"SignUp\", \"email\": \"" + createdAccount.getEmail() + "\"}");
      return createdAccount;
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
      final var res
        = restTemplate.getForEntity(String.format("%s/%d", serviceRegistryUrl, 2), ServiceRegistryResponseBody.class);
      if (res.getStatusCode() != HttpStatus.OK) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
      }
      ServiceRegistryResponseBody body = res.getBody();
      if (body == null || body.getServiceLocation() == null) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
      }
      final Account account = client.build()
        .get()
        .uri(String.format("%s/api/v1/accounts?email=%s", body.getServiceLocation(), email))
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
      return account;
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

  public String createAccessToken(String subject) {
    return jwt.generateToken(new HashMap<>(), subject, 300);  // expires in 5 minutes
  }

  public String createRefreshToken(String subject) {
    return jwt.generateToken(new HashMap<>(), subject, 31_536_000);  // expires in 1 year
  }
}
