package com.userManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//To send the JWT token back after login
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private  String token;


}
