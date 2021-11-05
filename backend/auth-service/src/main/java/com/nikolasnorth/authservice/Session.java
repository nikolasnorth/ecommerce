package com.nikolasnorth.authservice;

import javax.persistence.*;

@Entity
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(unique = true)
  private int accountId;

  @Column(nullable = false)
  private boolean isValid = true;

  protected Session() {
  }

  public Session(int id, int accountId, boolean isValid) {
    this.id = id;
    this.accountId = accountId;
    this.isValid = isValid;
  }

  public Session(int accountId) {
    this.accountId = accountId;
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
}
