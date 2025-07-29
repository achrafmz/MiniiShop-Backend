// src/main/java/com/minishop/product_service/controller/AnnonceController.java
package com.minishop.product_service.controller;

import com.minishop.product_service.model.Annonce;
import com.minishop.product_service.model.Category;
import com.minishop.product_service.service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/annonces")
@CrossOrigin(origins = "http://localhost:3000")
public class AnnonceController {

    private final String uploadDir = "uploads/";

    @Autowired
    private AnnonceService annonceService;

    @PostMapping
    public ResponseEntity<Annonce> createAnnonce(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("city") String city,
            @RequestParam("address") String address,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("userId") Long userId,
            @RequestParam("annoncerName") String annoncerName,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "photos", required = false) List<MultipartFile> photos) {

        Annonce annonce = new Annonce();
        annonce.setTitle(title);
        annonce.setDescription(description);
        annonce.setPrice(price);
        annonce.setCity(city);
        annonce.setAddress(address);
        annonce.setPhoneNumber(phoneNumber);
        annonce.setUserId(userId);
        annonce.setAnnoncerName(annoncerName);
        annonce.setCreationDate(LocalDateTime.now());
        annonce.setViews(0);

        // Associer la catégorie
        Category category = new Category();
        category.setId(categoryId);
        annonce.setCategory(category);

        List<String> photoUrls = new ArrayList<>();

        if (photos != null && !photos.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    return ResponseEntity.status(500).build();
                }
            }

            for (MultipartFile file : photos) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                try {
                    Files.copy(file.getInputStream(), filePath);
                    photoUrls.add("/uploads/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        annonce.setPhotos(photoUrls);
        Annonce saved = annonceService.createAnnonce(annonce);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonce(@PathVariable Long id) {
        return annonceService.getAnnonceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}