package com.nikolasnorth.accountservice;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {

  public Account getAccount(int id) {
    return new Account(1, "nikolas@uwo.ca", "Nikolas", 80, LocalDate.now(), LocalDate.now());
  }

  public List<Account> getAccounts() {
    return List.of(
      new Account(1, "nikolas@uwo.ca", "Nikolas", 80, LocalDate.now(), LocalDate.now())
    );
  }
}
