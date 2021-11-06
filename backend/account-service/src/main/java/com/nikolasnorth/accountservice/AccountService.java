package com.nikolasnorth.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account getAccount(int id) {
    try {
      return accountRepository.findById(id).orElseThrow();
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id '%d' does not exist.", id), e);
    }
  }

  public Account createAccount(Account account) {
    if (account.getEmail() == null
      || account.getEmail().length() == 0
      || account.getName() == null
      || account.getName().length() == 0
    ) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and name fields are required.");
    }
    if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        String.format("An account with email '%s' already exists.", account.getEmail()));
    }
    return accountRepository.save(account);
  }

  public void deleteAccount(int id) {
    try {
      accountRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id '%d' does not exist", id), e);
    }
  }
}
