package com.nikolasnorth.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public List<Account> getAccounts() {
    return accountRepository.findAll();
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
