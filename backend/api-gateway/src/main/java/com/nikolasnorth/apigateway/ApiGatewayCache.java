package com.nikolasnorth.apigateway;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ApiGatewayCache {

  private final Map<ECommerceService, String> serviceUris;

  public ApiGatewayCache() {
    this.serviceUris = new HashMap<>();
  }

  public Optional<String> get(ECommerceService service) {
    return Optional.of(serviceUris.get(service));
  }

  public void put(ECommerceService service, String uri) {
    serviceUris.put(service, uri);
  }

  public void remove(ECommerceService service) {
    serviceUris.remove(service);
  }
}
