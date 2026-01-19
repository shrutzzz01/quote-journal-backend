package com.quotejournal.repository;

import com.quotejournal.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(Role role);
    long countByIsVerified(boolean isVerified);
}
