package com.userManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DashboardController {

    @GetMapping("dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard(Authentication authentication) {
        String username = authentication.getName();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome, " + username + "!");
        response.put("username", username);

        return ResponseEntity.ok(response);
    }
}
