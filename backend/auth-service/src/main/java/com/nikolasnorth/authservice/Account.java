package com.nikolasnorth.authservice;

import java.time.LocalDate;

public class Account {

  private int id;

  private String email;

  private String name;

  private LocalDate createdAt;

  private LocalDate updatedAt;

  protected Account() {
  }

  public Account(int id, String email, String name, LocalDate createdAt, LocalDate updatedAt) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Account(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDate updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "Account{" +
      "id=" + id +
      ", email='" + email + '\'' +
      ", name='" + name + '\'' +
      ", createdAt=" + createdAt +
      ", updatedAt=" + updatedAt +
      '}';
  }
}
