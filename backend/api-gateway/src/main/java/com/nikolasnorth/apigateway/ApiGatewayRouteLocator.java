package com.nikolasnorth.apigateway;

import com.nikolasnorth.apigateway.filters.JwtFilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
public class ApiGatewayRouteLocator {

  private final RestTemplate restTemplate;

  private final String serviceRegistryUrl;

  @Autowired
  public ApiGatewayRouteLocator(RestTemplate restTemplate, @Value("${serviceRegistry.url}") String serviceRegistryUrl) {
    this.restTemplate = restTemplate;
    this.serviceRegistryUrl = serviceRegistryUrl;
  }

  @Bean
  public RouteLocator gateway(RouteLocatorBuilder builder, JwtFilterFactory jwtFilterFactory) {
    return builder.routes()
      .route(ECommerceService.ACCOUNT.label, p -> p
        .path("/api/v1/accounts")
        .or()
        .path("/api/v1/accounts/*")
        .filters(f -> f
          .filter(jwtFilterFactory.apply(new JwtFilterFactory.Config()))
          .changeRequestUri((serverWebExchange -> {
            try {
              final var res
                = restTemplate.getForEntity(String.format("%s/%d", serviceRegistryUrl, ECommerceService.ACCOUNT.id), ServiceRegistryResponseBody.class);
              if (res.getStatusCode() != HttpStatus.OK) {
                System.err.printf("Did not receive 200 status code for %s response%n", ECommerceService.ACCOUNT.label);
                serverWebExchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                return Optional.empty();
              }

              ServiceRegistryResponseBody body = res.getBody();
              if (body == null || body.getServiceLocation() == null) {
                System.err.printf("Did not receive 200 status code for %s response%n", ECommerceService.ACCOUNT.label);
                serverWebExchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                return Optional.empty();
              }

              return Optional.of(new URI(body.getServiceLocation() + serverWebExchange.getRequest().getPath()));
            } catch (URISyntaxException e) {
              System.err.println("Error occurred making URI");
              return Optional.empty();
            }
          })))
        .uri("no://op"))
      .route(ECommerceService.AUTH.label, p -> p
        .path("/api/v1/auth")
        .or()
        .path("/api/v1/auth/*")
        .filters(f -> f
          .changeRequestUri((serverWebExchange -> {
            try {
              final var res
                = restTemplate.getForEntity(String.format("%s/%d", serviceRegistryUrl, ECommerceService.AUTH.id), ServiceRegistryResponseBody.class);
              if (res.getStatusCode() != HttpStatus.OK) {
                System.err.printf("Did not receive 200 status code for %s response%n", ECommerceService.AUTH.label);
                serverWebExchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                return Optional.empty();
              }

              ServiceRegistryResponseBody body = res.getBody();
              if (body == null || body.getServiceLocation() == null) {
                System.err.printf("Did not receive 200 status code for %s response%n", ECommerceService.AUTH.label);
                serverWebExchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                return Optional.empty();
              }

              return Optional.of(new URI(body.getServiceLocation() + serverWebExchange.getRequest().getPath()));
            } catch (URISyntaxException e) {
              System.err.println("Error occurred making URI");
              return Optional.empty();
            }
          })))
        .uri("no://op"))
      .build();
  }
}
