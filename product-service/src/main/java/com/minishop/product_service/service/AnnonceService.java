// src/main/java/com/minishop/product_service/service/AnnonceService.java
package com.minishop.product_service.service;

import com.minishop.product_service.model.Annonce;
import com.minishop.product_service.model.Category;
import com.minishop.product_service.repository.AnnonceRepository;
import com.minishop.product_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    public Annonce createAnnonce(Annonce annonce) {
        annonce.setCreationDate(java.time.LocalDateTime.now());

        if (annonce.getCategory() != null && annonce.getCategory().getId() != null) {
            Category category = categoryRepository.findById(annonce.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
            annonce.setCategory(category);
        }

        return annonceRepository.save(annonce);
    }

    public Annonce updateAnnonce(Long id, Annonce annonceDetails) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));

        annonce.setTitle(annonceDetails.getTitle());
        annonce.setDescription(annonceDetails.getDescription());
        annonce.setPrice(annonceDetails.getPrice());
        annonce.setCity(annonceDetails.getCity());
        annonce.setAddress(annonceDetails.getAddress());
        annonce.setPhoneNumber(annonceDetails.getPhoneNumber());
        annonce.setUserId(annonceDetails.getUserId());
        annonce.setAnnoncerName(annonceDetails.getAnnoncerName());

        if (annonceDetails.getPhotos() != null) {
            annonce.setPhotos(annonceDetails.getPhotos());
        }

        if (annonceDetails.getCategory() != null && annonceDetails.getCategory().getId() != null) {
            Category category = categoryRepository.findById(annonceDetails.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
            annonce.setCategory(category);
        }

        return annonceRepository.save(annonce);
    }

    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }

    public void incrementViews(Long id) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
        annonce.setViews(annonce.getViews() + 1);
        annonceRepository.save(annonce);
    }
}