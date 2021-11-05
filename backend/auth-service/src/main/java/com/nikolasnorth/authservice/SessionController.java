package com.nikolasnorth.authservice;

import com.nikolasnorth.authservice.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/auth")
public class SessionController {

  private final SessionService sessionService;

  @Autowired
  public SessionController(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @PostMapping("signup")
  public Account signUp(@RequestBody Account account) {
    return new Account(1, "new.account@uwo.ca", "New Account Name", LocalDate.now(), LocalDate.now());
  }

  @PostMapping("signin")
  public Account signIn(@RequestBody Account account) {
    return new Account(1, "new.account@uwo.ca", "New Account Name", LocalDate.now(), LocalDate.now());
  }

  @GetMapping("signout")
  public void signOut(@RequestBody Account account) {

  }
}
