// src/main/java/com/minishop/product_service/model/User.java
package com.minishop.product_service.model;

public class User {
    private Long id;
    private String username;
    private String email;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}