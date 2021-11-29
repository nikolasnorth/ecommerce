package com.nikolasnorth.authservice.auth;

import com.nikolasnorth.authservice.entities.Account;
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
  public ResponseEntity<Map<String, Object>> signUp(@RequestBody Map<String, String> req, HttpServletResponse res) {
    final Account account = new Account(req.get("email"), req.get("name"));
    final Account createdAccount = authService.signUp(account, req.get("password"));
    return ResponseEntity.ok(Map.of(
      "account", createdAccount,
      "accessToken", authService.createAccessToken(Integer.toString(createdAccount.getId())),
      "refreshToken", authService.createRefreshToken(Integer.toString(createdAccount.getId()))
    ));
  }

  @PostMapping("signin")
  public ResponseEntity<Map<String, Object>> signIn(@RequestBody Map<String, String> req, HttpServletResponse res) {
    final Account account = authService.signIn(req.get("email"), req.get("password"));
    return ResponseEntity.ok(Map.of(
      "account", account,
      "accessToken", authService.createAccessToken(Integer.toString(account.getId())),
      "refreshToken", authService.createRefreshToken(Integer.toString(account.getId()))
    ));
  }

  @GetMapping("signout")
  public void signOut(@RequestBody Account account) {

  }
}
