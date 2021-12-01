package com.nikolasnorth.apigateway;

public enum ECommerceService {
  ACCOUNT("account"),
  AUTH("auth");

  public final String label;

  ECommerceService(String label) {
    this.label = label;
  }
}
