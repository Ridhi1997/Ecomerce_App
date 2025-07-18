package com.userManagement.config;

import com.userManagement.security.JwtAuthFilter;
import com.userManagement.service.CustomUserDetailsService;
import com.userManagement.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

 private final JwtService jwtService;
 private final JwtAuthFilter jwtAuthFilter;
 private final CustomUserDetailsService customUserDetailsService;

 public SecurityConfig(JwtService jwtService, JwtAuthFilter jwtAuthFilter, CustomUserDetailsService customUserDetailsService) {
  this.jwtService = jwtService;
  this.jwtAuthFilter = jwtAuthFilter;
  this.customUserDetailsService = customUserDetailsService;
 }

 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  http.csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
                  .requestMatchers(
                          "/auth/**",
                          "/swagger-ui/**",
                          "/v3/api-docs/**",
                          "/swagger-ui.html",
                          "/webjars/**"
                  ).permitAll()
                  .requestMatchers("/admin/**").hasRole("ADMIN")
                  .anyRequest().authenticated())
          .sessionManagement(session -> session
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authenticationProvider(authenticationProvider())
          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  return http.build();
 }



 @Bean
 public CorsConfigurationSource corsConfigurationSource() {
  CorsConfiguration configuration = new CorsConfiguration();
  configuration.addAllowedOrigin("http://localhost:5174");
  configuration.addAllowedMethod("*");
  configuration.addAllowedHeader("*");
  configuration.setAllowCredentials(true);

  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  source.registerCorsConfiguration("/**", configuration);
  return source;
 }

 @Bean
 public AuthenticationProvider authenticationProvider() {
  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
  authProvider.setUserDetailsService(customUserDetailsService);
  authProvider.setPasswordEncoder(passwordEncoder());
  return authProvider;
 }

 @Bean
 public PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();  // Return PasswordEncoder bean
 }

 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
  return config.getAuthenticationManager();
 }


}