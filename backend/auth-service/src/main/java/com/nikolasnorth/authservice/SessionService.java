package com.nikolasnorth.authservice;

import com.nikolasnorth.authservice.account.Account;
import com.nikolasnorth.authservice.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

  private final SessionRepository sessionRepository;

  @Autowired
  public SessionService(SessionRepository s, AccountRepository a) {
    this.sessionRepository = s;
  }

  public void signUp(Account account) {
    System.out.printf("Account %d has signed up!%n", account.getId());
  }

  public void signIn(Account account) {
    System.out.printf("Account %d has signed in!%n", account.getId());
  }

  public void signOut(Account account) {
    System.out.printf("Account %d has signed out!%n", account.getId());
  }
}
