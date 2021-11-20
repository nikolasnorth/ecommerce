package com.nikolasnorth.authservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Auth, Integer> {

}
