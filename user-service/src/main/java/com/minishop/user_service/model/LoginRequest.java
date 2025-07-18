// src/main/java/com/minishop/user/model/LoginRequest.java
package com.minishop.user_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}