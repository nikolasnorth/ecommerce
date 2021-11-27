package com.nikolasnorth.authservice.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Configuration
@EnableJpaAuditing
public class AuthConfig {

  @Bean
  CommandLineRunner runner(AuthRepository repo) {
    return args -> repo.saveAll(List.of(
      new Auth(1, "password1"),
      new Auth(2, "password2"),
      new Auth(3, "password3")
    ));
  }
}
