package com.nikolasnorth.apigateway;

public enum ECommerceService {
  ACCOUNT(2, "Account_Service"),
  AUTH(3, "Auth_Service");

  public final int id;

  public final String label;

  ECommerceService(int id, String label) {
    this.id = id;
    this.label = label;
  }
}
