package com.nikolasnorth.authservice.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

  @Query("select a from Auth a where a.accountId = ?1")
  Optional<Auth> findByAccountId(int accountId);
}
