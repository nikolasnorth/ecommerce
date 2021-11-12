package com.nikolasnorth.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService sessionService;

  private final WebClient.Builder client;

  @Autowired
  public AuthController(AuthService s, WebClient.Builder w) {
    this.sessionService = s;
    this.client = w;
  }

  @PostMapping("signup")
  public ResponseEntity<Map<String, Account>> signUp(@RequestBody Account account) {
    final var created = new Account(1, "new.entity@uwo.ca", "New Account Name", LocalDate.now(), LocalDate.now());
    final Map<String, Account> res = Map.of("entity", created);
    return ResponseEntity.ok(res);
  }

  @PostMapping("signin")
  public ResponseEntity<Map<String, Account>> signIn(@RequestBody Account account) {
    return ResponseEntity.ok(Map.of("entity", sessionService.signIn(account)));
  }

  @GetMapping("signout")
  public void signOut(@RequestBody Account account) {

  }
}
