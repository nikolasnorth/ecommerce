package com.nikolasnorth.authservice.deserialize;

import com.nikolasnorth.authservice.Account;

public class GetAccountResponse {

  private Account account;

  protected GetAccountResponse() {
  }

  public GetAccountResponse(Account account) {
    this.account = account;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
