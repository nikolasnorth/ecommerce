package com.nikolasnorth.authservice;

import com.nikolasnorth.authservice.entities.Account;
import com.nikolasnorth.authservice.entities.AccountCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService s) {
    this.authService = s;
  }

  @PostMapping("signup")
  public ResponseEntity<Account> signUp(@RequestBody Map<String, String> req, HttpServletResponse res) {
    final Account account = new Account(req.get("email"), req.get("name"));
    final AccountCookies accountCookies = authService.signUp(account, req.get("password"));
    res.addCookie(accountCookies.getAccessTokenCookie());
    res.addCookie(accountCookies.getRefreshTokenCookie());
    return ResponseEntity.ok(accountCookies.getAccount());
  }

  @PostMapping("signin")
  public ResponseEntity<Account> signIn(@RequestBody Map<String, String> req, HttpServletResponse res) {
    final AccountCookies accountCookies = authService.signIn(req.get("email"), req.get("password"));
    res.addCookie(accountCookies.getAccessTokenCookie());
    res.addCookie(accountCookies.getRefreshTokenCookie());
    return ResponseEntity.ok(accountCookies.getAccount());
  }

  @GetMapping("signout")
  public void signOut(@RequestBody Account account) {

  }
}
