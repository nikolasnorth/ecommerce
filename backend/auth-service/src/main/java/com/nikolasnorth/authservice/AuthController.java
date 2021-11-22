package com.nikolasnorth.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
    final Account created = authService.signUp(account, req.get("password"));
    final Cookie accessTokenCookie = authService.createAccessTokenCookie(Integer.toString(created.getId()));
    final Cookie refreshTokenCookie = authService.createRefreshTokenCookie(Integer.toString(created.getId()));
    res.addCookie(accessTokenCookie);
    res.addCookie(refreshTokenCookie);
    return ResponseEntity.ok(created);
  }

  @PostMapping("signin")
  public ResponseEntity<Map<String, Account>> signIn(@RequestBody Map<String, String> req) {
    return ResponseEntity.ok(Map.of("account", authService.signIn(req.get("email"), req.get("password"))));
  }

  @GetMapping("signout")
  public void signOut(@RequestBody Account account) {

  }
}
