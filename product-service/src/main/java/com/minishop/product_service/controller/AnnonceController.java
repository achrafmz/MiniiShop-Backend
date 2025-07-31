// src/main/java/com/minishop/product_service/controller/AnnonceController.java
package com.minishop.product_service.controller;

import com.minishop.product_service.client.UserClient;
import com.minishop.product_service.model.Annonce;
import com.minishop.product_service.model.User;
import com.minishop.product_service.service.AnnonceService;
import com.minishop.product_service.service.EmailService;
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
    @Autowired
    private UserClient userClient;

    @Autowired
    private EmailService emailService;
    private final String uploadDir = "uploads/";

    @Autowired
    private AnnonceService annonceService;

    // src/main/java/com/minishop/product_service/controller/AnnonceController.java
    @PostMapping
    public ResponseEntity<Annonce> createAnnonce(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("city") String city,
            @RequestParam("address") String address,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("username") String username,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "photos", required = false) List<MultipartFile> photos) {

        // 🔽 Récupérer l'email via UserClient
        User user = userClient.getUserByUsername(username);
        String userEmail = user.getEmail();

        // Créer l'annonce
        Annonce annonce = new Annonce();
        annonce.setTitle(title);
        annonce.setDescription(description);
        annonce.setPrice(price);
        annonce.setCity(city);
        annonce.setAddress(address);
        annonce.setPhoneNumber(phoneNumber);
        annonce.setUsername(username);
        annonce.setCategoryId(categoryId);
        annonce.setCreationDate(LocalDateTime.now());
        annonce.setViews(0);

        // Gérer les photos
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

        // 🔥 Enregistrer l'annonce
        Annonce saved = annonceService.createAnnonce(annonce);

        // ✅ Envoyer l'email avec les 3 paramètres
        emailService.sendAnnoncePublishedEmail(userEmail, username, title); // ✅ title ajouté ici

        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceService.getAllAnnonces();
    }

    @GetMapping("/category/{categoryId}")
    public List<Annonce> getAnnoncesByCategory(@PathVariable Long categoryId) {
        return annonceService.getAnnoncesByCategoryId(categoryId);
    }

    @GetMapping("/user/{username}")
    public List<Annonce> getAnnoncesByUsername(@PathVariable String username) {
        return annonceService.getAnnoncesByUsername(username);
    }

}