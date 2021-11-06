package com.nikolasnorth.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("{id}")
  public ResponseEntity<Map<String, Account>> getAccount(@PathVariable("id") int id) {
    return ResponseEntity.ok(Map.of("account", accountService.getAccount(id)));
  }

  @PostMapping()
  public void createAccount(@RequestBody Account account) {
    accountService.createAccount(account);
  }

  @DeleteMapping("{id}")
  public void deleteAccount(@PathVariable("id") int id) {
    accountService.deleteAccount(id);
  }
}
