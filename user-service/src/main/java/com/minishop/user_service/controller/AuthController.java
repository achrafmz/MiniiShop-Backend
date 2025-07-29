package com.minishop.user_service.controller;

import com.minishop.user_service.model.User;
import com.minishop.user_service.payload.GoogleLoginRequest;
import com.minishop.user_service.payload.LoginRequest;
import com.minishop.user_service.payload.RegisterRequest;
import com.minishop.user_service.repository.UserRepository;
import com.minishop.user_service.security.JwtUtil;
import com.minishop.user_service.service.EmailService;
import com.minishop.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userRepository.save(user);

        // 📧 Envoie un email de confirmation
        emailService.sendRegistrationConfirmation(request.getEmail(), request.getUsername());

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        var userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        return ResponseEntity.ok("Login successful. Welcome " + user.getUsername());
    }

//    @PostMapping("/google-login")
//    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
//        try {
//            // 🔍 Vérifie le token Google
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
//                    JacksonFactory.getDefaultInstance())
//                    .setAudience(Collections.singletonList("TON_CLIENT_ID_GOOGLE"))
//                    .build();
//
//            GoogleIdToken idToken = verifier.verify(request.getIdToken());
//            if (idToken == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide");
//            }
//
//            GoogleIdToken.Payload payload = idToken.getPayload();
//
//            String email = payload.getEmail();
//            String firstName = (String) payload.get("given_name");
//            String lastName = (String) payload.get("family_name");
//
//            // 🧑 Trouve ou crée l’utilisateur dans ta base
//            User user = userRepository.findByEmail(email)
//                    .orElseGet(() -> {
//                        User newUser = new User();
//                        newUser.setEmail(email);
//                        newUser.setFirstName(firstName);
//                        newUser.setLastName(lastName);
//                        newUser.setUsername(email.split("@")[0]);
//                        newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
//                        return userRepository.save(newUser);
//                    });
//
//            // 🔐 Génère ton propre JWT
//            String myJwtToken = JwtUtil.generateToken(user.getUsername());
//
//            return ResponseEntity.ok()
//                    .header("Authorization", "Bearer " + myJwtToken)
//                    .body("Connexion réussie avec Google");
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l’authentification Google");
//        }
//    }

    private String generateUsernameFromEmail(String email) {
        String baseUsername = email.split("@")[0];
        return userRepository.findByUsername(baseUsername).isPresent()
                ? baseUsername + System.currentTimeMillis() % 1000
                : baseUsername;
    }

}