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
  public ResponseEntity<Account> getAccountByEmail(@RequestParam String email) {
    return ResponseEntity.ok(accountService.getAccountByEmail(email));
  }

  @GetMapping("{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable("id") int id) {
    return ResponseEntity.ok(accountService.getAccountById(id));
  }

  @PostMapping()
  public ResponseEntity<Account> createAccount(@RequestBody Account account) {
    return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
  }

  @PutMapping("{id}")
  public ResponseEntity<Map<String, Account>> updateAccount(@PathVariable("id") int id, @RequestBody Account account) {
    return ResponseEntity.ok(Map.of("account", accountService.updateAccount(id, account)));
  }

  @DeleteMapping("{id}")
  public void deleteAccount(@PathVariable("id") int id) {
    accountService.deleteAccount(id);
  }

  @GetMapping("/healthstatus")
  public ResponseEntity<?> healthStatus() {
    return ResponseEntity.ok().build();
  }
}
