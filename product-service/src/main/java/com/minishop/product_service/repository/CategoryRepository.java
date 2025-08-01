// src/main/java/com/minishop/product_service/repository/CategoryRepository.java
package com.minishop.product_service.repository;

import com.minishop.product_service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}