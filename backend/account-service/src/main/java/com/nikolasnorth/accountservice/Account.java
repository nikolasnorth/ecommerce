package com.nikolasnorth.accountservice;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String name;

  @CreatedDate
  private LocalDate createdAt;

  @LastModifiedDate
  private LocalDate updatedAt;

  protected Account() {
  }

  public Account(int id, String email, String name, int avgRating, LocalDate createdAt, LocalDate updatedAt) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Account(String email, String name, int avgRating) {
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
