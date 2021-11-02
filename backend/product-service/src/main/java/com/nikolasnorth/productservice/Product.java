package com.nikolasnorth.productservice;

import java.time.LocalDate;

public class Product {

  private int id;

  private String name;

  private String description;

  private int priceInCents;

  private int vendorId;

  private LocalDate createdAt;

  private LocalDate updatedAt;

  protected Product() {
  }

  public Product(int id, String name, String description, int priceInCents, int vendorId, LocalDate createdAt, LocalDate updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.priceInCents = priceInCents;
    this.vendorId = vendorId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Product(String name, String description, int priceInCents, int vendorId) {
    this.name = name;
    this.description = description;
    this.priceInCents = priceInCents;
    this.vendorId = vendorId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getPriceInCents() {
    return priceInCents;
  }

  public void setPriceInCents(int priceInCents) {
    this.priceInCents = priceInCents;
  }

  public int getVendorId() {
    return vendorId;
  }

  public void setVendorId(int vendorId) {
    this.vendorId = vendorId;
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
    return "Product{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      ", priceInCents=" + priceInCents +
      ", vendorId=" + vendorId +
      ", createdAt=" + createdAt +
      ", updatedAt=" + updatedAt +
      '}';
  }
}
