package com.finance.controller;
import com.finance.dto.*;
import com.finance.entity.User;
import com.finance.repository.UserRepository;
import com.finance.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 @PostMapping("/register")
 public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){
  String email=r.email().trim().toLowerCase();
  if(users.existsByEmail(email)) return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered.");
  users.save(new User(r.name().trim(),email,encoder.encode(r.password())));
  return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Please login.");
 }
 @PostMapping("/login")
 public ResponseEntity<?> login(@Valid @RequestBody LoginRequest r){
  String email=r.email().trim().toLowerCase(); User u=users.findByEmail(email).orElse(null);
  if(u==null||!encoder.matches(r.password(),u.getPassword())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
  return ResponseEntity.ok(new AuthResponse(jwt.generateToken(u.getEmail()),u.getName(),u.getEmail()));
 }
}