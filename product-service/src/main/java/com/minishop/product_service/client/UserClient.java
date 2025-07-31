// src/main/java/com/minishop/product_service/client/UserClient.java
package com.minishop.product_service.client;

import com.minishop.product_service.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8084")
public interface UserClient {
    @GetMapping("/api/users/username/{username}")
    User getUserByUsername(@PathVariable("username") String username);
}