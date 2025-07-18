// src/main/java/com/minishop/user/controller/AuthController.java
package com.minishop.user_service.controller;

import com.minishop.user_service.model.LoginRequest;
import com.minishop.user_service.model.User;
import com.minishop.user_service.repository.UserRepository;
import com.minishop.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur déjà pris");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return ResponseEntity.ok("Inscription réussie !");
    }
}