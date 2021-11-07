package com.nikolasnorth.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered with an account.");
    }
    return accountRepository.save(account);
  }

  public Account updateAccount(int id, Account updated) {
    try {
      Account existing = accountRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id '%d' does not exist", id)));
      if (updated.getName() != null && !updated.getName().isEmpty() && !updated.getName().equals(existing.getName())) {
        existing.setName(updated.getName());
      }
      if (updated.getEmail() != null
        && !updated.getEmail().isEmpty()
        && !updated.getEmail().equals(existing.getEmail())
      ) {
        existing.setEmail(updated.getEmail());
      }
      return accountRepository.save(existing);
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered with an account.", e);
    }
  }

  public void deleteAccount(int id) {
    try {
      accountRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id '%d' does not exist", id), e);
    }
  }
}
