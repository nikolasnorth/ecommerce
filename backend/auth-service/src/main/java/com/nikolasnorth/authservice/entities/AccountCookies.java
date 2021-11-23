package com.nikolasnorth.authservice.entities;

import javax.servlet.http.Cookie;
import java.util.Objects;

public class AccountCookies {

  private final Cookie accessToken;

  private final Cookie refreshToken;

  private final Account account;

  public AccountCookies(Cookie accessTokenCookie, Cookie refreshTokenCookie, Account account) {
    this.accessToken = Objects.requireNonNull(accessTokenCookie, "Access token cookie must not be null.");
    this.refreshToken = Objects.requireNonNull(refreshTokenCookie, "Refresh token cookie must not be null.");
    this.account = Objects.requireNonNull(account, "Account must not be null.");
  }

  public Cookie getAccessTokenCookie() {
    return accessToken;
  }

  public Cookie getRefreshTokenCookie() {
    return refreshToken;
  }

  public Account getAccount() {
    return account;
  }
}
