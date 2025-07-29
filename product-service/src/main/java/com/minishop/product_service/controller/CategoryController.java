// src/main/java/com/minishop/product_service/controller/CategoryController.java
package com.minishop.product_service.controller;

import com.minishop.product_service.model.Category;
import com.minishop.product_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final String uploadDir = "uploads/";

    @Autowired
    private CategoryService categoryService;

    // POST - Créer une catégorie + upload photo
    // src/main/java/com/minishop/product_service/controller/CategoryController.java
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestParam("name") String name,
            @RequestParam("photo") MultipartFile photo) {

        // Créer le dossier uploads
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                return ResponseEntity.status(500).build();
            }
        }

        // Sauvegarder le fichier
        String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        try {
            Files.copy(photo.getInputStream(), filePath);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }

        // Créer la catégorie
        Category category = new Category();
        category.setName(name);
        category.setPhotoUrl("/uploads/" + fileName);

        Category saved = categoryService.createCategory(category);
        return ResponseEntity.ok(saved);
    }

    // Autres routes (GET, PUT, DELETE) restent inchangées
    @GetMapping
    public java.util.List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        try {
            Category updated = categoryService.updateCategory(id, categoryDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}