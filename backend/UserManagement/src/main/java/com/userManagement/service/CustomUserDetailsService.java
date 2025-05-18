package com.userManagement.service;

import com.userManagement.entity.RegisterRequest;
import com.userManagement.entity.Role;
import com.userManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.userManagement.entity.User user = userRepository.findByusername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return  User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(Role.USER.name())
                .build();


    }
}
