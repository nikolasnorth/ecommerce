package com.nikolasnorth.authservice.account;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class AccountRepository {

  public AccountRepository() {
  }

  public Optional<Account> getAccount(int id) {
    return Optional.of(new Account(1, "nikolas@uwo.ca", "Nikolas", LocalDate.now(), LocalDate.now()));
  }
}
