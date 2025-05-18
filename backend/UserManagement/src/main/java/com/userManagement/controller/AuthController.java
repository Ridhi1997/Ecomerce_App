package com.userManagement.controller;

import com.userManagement.entity.LoginRequest;
import com.userManagement.entity.RegisterRequest;
import com.userManagement.service.JwtService;
import com.userManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    @Autowired
    private  UserService userService;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    // User Registration (Sign Up)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        // Register the user
        return ResponseEntity.ok(userService.register(registerRequest));  // Returns a success message
    }

    // User Authentication (Login)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Generate JWT token after authenticating the user
        String token = userService.authenticate(loginRequest);

        // Return the generated JWT token
        return ResponseEntity.ok("Bearer " + token);  // Send token with "Bearer" prefix
    }
}
