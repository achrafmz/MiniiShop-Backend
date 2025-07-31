// src/main/java/com/minishop/product_service/repository/AnnonceRepository.java
package com.minishop.product_service.repository;

import com.minishop.product_service.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
}