package com.nikolasnorth.accountservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Configuration
@EnableJpaAuditing
public class AccountConfig {

  @Bean
  CommandLineRunner runner(AccountRepository repo) {
    return args -> {
      repo.saveAll(List.of(
        new Account("nikolas@uwo.ca", "Nikolas", 80),
        new Account("john.doe@uwo.ca", "John Doe", 71),
        new Account("jane.doe@uwo.ca", "Jane Doe", 90)
      ));
    };
  }
}
