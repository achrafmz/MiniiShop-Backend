//// src/main/java/com/minishop/user/controller/UserController.java
//package com.minishop.user_service.controller;
//
//import com.minishop.user_service.model.User;
//import com.minishop.user_service.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:3000")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // GET - Liste des utilisateurs
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    // GET - Utilisateur par ID
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return userService.getUserById(id).orElse(null);
//    }
//
//    // POST - Créer un utilisateur
//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        return userService.createUser(user);
//    }
//
//    // PUT - Mettre à jour un utilisateur
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
//        userDetails.setId(id);
//        return userService.updateUser(userDetails);
//    }
//
//    // DELETE - Supprimer un utilisateur
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        userService.deleteUserById(id);
//    }
//}