package com.nikolasnorth.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @GetMapping()
  public ResponseEntity<Account> getAccount(@RequestParam String email) {
    return ResponseEntity.ok(accountService.getAccountByEmail(email));
  }

  @GetMapping("{id}")
  public ResponseEntity<Account> getAccount(@PathVariable("id") int id) {
    return ResponseEntity.ok(accountService.getAccount(id));
  }

  @PostMapping()
  public ResponseEntity<Map<String, Account>> createAccount(@RequestBody Account account) {
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("account", accountService.createAccount(account)));
  }

  @PutMapping("{id}")
  public ResponseEntity<Map<String, Account>> updateAccount(@PathVariable("id") int id, @RequestBody Account account) {
    return ResponseEntity.ok(Map.of("account", accountService.updateAccount(id, account)));
  }

  @DeleteMapping("{id}")
  public void deleteAccount(@PathVariable("id") int id) {
    accountService.deleteAccount(id);
  }
}
