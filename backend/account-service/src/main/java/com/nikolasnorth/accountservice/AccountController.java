package com.nikolasnorth.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("{id}")
  public Account getAccount(@PathVariable("id") int id) {
    return accountService.getAccount(id);
  }

  @PostMapping()
  public void createAccount(@RequestBody Account account) {
    accountService.createAccount(account);
  }

  @DeleteMapping("{id}")
  public void deleteAccount(@PathVariable("id") int id) {
    try {
      accountService.deleteAccount(id);
    } catch (IllegalArgumentException ignored) {
    }
  }
}
