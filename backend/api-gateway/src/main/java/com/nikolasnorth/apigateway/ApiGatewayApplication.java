package com.nikolasnorth.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

  @Bean
  public RouteLocator gateway(RouteLocatorBuilder builder) {
    return builder.routes()
      .route("account-service", p -> p
        .path("/api/v1/accounts/*")
        .uri("http://localhost:8082/"))
      .route("auth-service", p -> p
        .path("/api/v1/auth/*")
        .uri("http://localhost:8085/"))
      .build();
  }

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

}
