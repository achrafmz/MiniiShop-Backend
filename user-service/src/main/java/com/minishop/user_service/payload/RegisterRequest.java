// src/main/java/com/minishop/user_service/payload/RegisterRequest.java
package com.minishop.user_service.payload;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;   // ← Ajouté
    private String lastName;    // ← Ajouté

    // Getters et Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // --- Ajoute ces méthodes ---

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}