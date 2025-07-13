package com.userManagement.service;

import com.userManagement.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private  String jwtSecrete;

    @Value("${jwt.expiration}")
    private  Long expirationTime;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecrete.getBytes());
    }
    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public  <T> T extractClaim(String jwt, Function<Claims, T> resolver) {
        final Claims claims = Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return  resolver.apply(claims);
    }


    public boolean isTokenValid(String jwt, User user) {
        final  String username = extractUserName(jwt);
        return (username.equals(user.getUsername()) && !isTokenExpired(jwt));  // Validate token by username and expiration
    }

    private boolean isTokenExpired(String jwt) {
        return extractClaim(jwt,Claims::getExpiration).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        // Create a signing key using the secret key
        Key key = Keys.hmacShaKeyFor(jwtSecrete.getBytes());
        return Jwts.builder()
                .claim("sub", userDetails.getUsername())  // Custom claim for subject
                .claim("iat", new Date())  // Issued at (issuedAt)
                .claim("exp", new Date(System.currentTimeMillis() + expirationTime))  // Expiration time
                .signWith(key, SignatureAlgorithm.HS256)// Sign with the key and algorithm
                .compact();  // Generate the compact JWT string
    }
}
