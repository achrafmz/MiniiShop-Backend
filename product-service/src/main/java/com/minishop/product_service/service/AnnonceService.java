// src/main/java/com/minishop/product_service/service/AnnonceService.java
package com.minishop.product_service.service;

import com.minishop.product_service.model.Annonce;
import com.minishop.product_service.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnonceService {

    
    @Autowired
    private AnnonceRepository annonceRepository;

    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    public Annonce createAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    public List<Annonce> getAnnoncesByCategoryId(Long categoryId) {
        return annonceRepository.findByCategoryId(categoryId);
    }

    public List<Annonce> getAnnoncesByUsername(String username) {
        return annonceRepository.findByUsername(username);
    }

}