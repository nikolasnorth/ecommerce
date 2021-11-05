package com.nikolasnorth.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account getAccount(int id) {
    return accountRepository.findById(id).orElseThrow();
  }

  public void createAccount(Account account) {
    if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
      throw new IllegalArgumentException(String.format("An account with email '%s' already exists.", account.getEmail()));
    }
    accountRepository.save(account);
  }

  public void deleteAccount(int id) {
    try {
      accountRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ignored) {
    }
  }
}
