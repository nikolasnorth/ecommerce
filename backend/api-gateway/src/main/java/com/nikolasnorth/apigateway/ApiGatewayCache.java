package com.nikolasnorth.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ApiGatewayCache {

  private final Map<ECommerceService, String> serviceUris;

  private final RestTemplate restTemplate;

  private final String serviceRegistryUrl;

  @Autowired
  public ApiGatewayCache(RestTemplate restTemplate, @Value("${serviceRegistry.url}") String serviceRegistryUrl) {
    this.serviceUris = new HashMap<>();
    this.restTemplate = restTemplate;
    this.serviceRegistryUrl = serviceRegistryUrl;
    refresh();
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

  private void refresh() {
    ECommerceService[] services = ECommerceService.values();
    for (ECommerceService service : services) {
      final ResponseEntity<ServiceRegistryResponseBody> res
        = restTemplate.getForEntity(String.format("%s/%d", serviceRegistryUrl, service.id), ServiceRegistryResponseBody.class);
      if (res.getStatusCode() != HttpStatus.OK) {
        System.err.printf("Did not receive 200 status code for %s response%n", service.label);
        continue;
      }
      ServiceRegistryResponseBody body = res.getBody();
      if (body == null || body.getServiceLocation() == null) {
        System.err.printf("Service location for %s response was null%n", service.label);
        continue;
      }
      this.put(service, body.getServiceLocation());
    }
  }
}
