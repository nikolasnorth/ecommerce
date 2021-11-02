package com.nikolasnorth.accountservice;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AccountService {

  public Account getAccount(int id) {
    return new Account(1, "nikolas@uwo.ca", "Nikolas", 80, LocalDate.now(), LocalDate.now());
  }
}
