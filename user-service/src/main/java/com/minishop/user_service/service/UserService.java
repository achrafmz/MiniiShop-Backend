// src/main/java/com/minishop/user/service/UserService.java
package com.minishop.user_service.service;

import com.minishop.user_service.model.User;
import com.minishop.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lire tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Créer un utilisateur
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Trouver un utilisateur par ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Trouver un utilisateur par username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Mettre à jour un utilisateur
    public User updateUser(User userDetails) {
        return userRepository.save(userDetails);
    }

    // Supprimer un utilisateur
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}