package com.alikaracor.learning.springsecuritybasicslab.repository;

import com.alikaracor.learning.springsecuritybasicslab.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository  extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);
}