package com.nikolasnorth.apigateway;

import com.nikolasnorth.apigateway.filters.JwtFilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ApiGatewayRouteLocator {

  private final ApiGatewayCache cache;

  @Autowired
  public ApiGatewayRouteLocator(ApiGatewayCache cache) {
    this.cache = cache;
  }

  @Bean
  public RouteLocator gateway(RouteLocatorBuilder builder, JwtFilterFactory jwtFilterFactory) {
    return builder.routes()
      .route(ECommerceService.ACCOUNT.label, p -> p
        .path("/api/v1/accounts")
        .or()
        .path("/api/v1/accounts/*")
        .filters(f -> f.filter(jwtFilterFactory.apply(new JwtFilterFactory.Config())))
        .uri("http://localhost:8082/"))
      .route(ECommerceService.AUTH.label, p -> p
        .path("/api/v1/auth")
        .or()
        .path("/api/v1/auth/*")
        .uri("http://localhost:8085/"))
      .build();
  }
}
