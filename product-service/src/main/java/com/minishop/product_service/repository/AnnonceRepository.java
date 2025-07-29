// src/main/java/com/minishop/product_service/repository/AnnonceRepository.java
package com.minishop.product_service.repository;

import com.minishop.product_service.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByCategoryId(Long categoryId);
    List<Annonce> findByCity(String city);
    List<Annonce> findByUserId(Long userId);
}