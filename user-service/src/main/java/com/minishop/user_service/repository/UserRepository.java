// src/main/java/com/minishop/user/repository/UserRepository.java
package com.minishop.user_service.repository;

import com.minishop.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}