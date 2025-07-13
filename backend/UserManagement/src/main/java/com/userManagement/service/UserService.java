package com.userManagement.service;

import com.userManagement.dto.CustomUserDetails;
import com.userManagement.dto.LoginRequest;
import com.userManagement.dto.RegisterRequest;
import com.userManagement.dto.Role;
import com.userManagement.entity.*;
import com.userManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    // Register a new user
    public String register(RegisterRequest registerRequest) {
        if (userRepository.findByusername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Encrypt password
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Create and save user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Role.USER);// Assuming you pass role (USER/ADMIN)

        userRepository.save(user);
        return "User registered successfully!";
    }

    // Authenticate user and generate token
    public String authenticate(LoginRequest loginRequest) {
        User user = (User) userRepository.findByusername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Wrap the User entity in CustomUserDetails
        UserDetails userDetails = new CustomUserDetails(user);

        // Generate JWT Token using the CustomUserDetails object
        return jwtService.generateToken(userDetails);
    }


}
