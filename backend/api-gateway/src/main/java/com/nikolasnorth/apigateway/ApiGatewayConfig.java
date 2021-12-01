package com.nikolasnorth.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class ApiGatewayConfig {

  @Bean
  public CorsWebFilter corsWebFilter() {
    final CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
    corsConfig.setMaxAge(3600L);
    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    corsConfig.addAllowedHeader("*");
    corsConfig.setAllowCredentials(true);

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}

