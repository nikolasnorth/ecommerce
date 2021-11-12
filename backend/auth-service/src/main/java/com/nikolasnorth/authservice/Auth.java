package com.nikolasnorth.authservice;

import javax.persistence.*;

@Entity
public class Auth {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(unique = true)
  private int accountId;

  @Column(nullable = false)
  private boolean isValid = true;

  @Column(nullable = false)
  private String password;

  protected Auth() {
  }

  public Auth(int id, int accountId, boolean isValid, String password) {
    this.id = id;
    this.accountId = accountId;
    this.isValid = isValid;
    this.password = password;
  }

  public Auth(int accountId, String password) {
    this.accountId = accountId;
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
