package com.example.WorkoutTrackingApp.auth.service;

import com.example.WorkoutTrackingApp.auth.Enum.Role;
import com.example.WorkoutTrackingApp.auth.dto.AuthenticationRequest;
import com.example.WorkoutTrackingApp.auth.dto.AuthenticationResponse;
import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.auth.repository.UserRepository;
import com.example.WorkoutTrackingApp.auth.service.JwtService;
import com.example.WorkoutTrackingApp.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
    User user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .isActive(true)
        .build();
    repository.save(user);
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", user.getRole().name());
    var jwtToken = jwtService.generateToken(extraClaims, user);
    AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
    return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
  }


  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", user.getRole().name());
    var jwtToken = jwtService.generateToken(extraClaims, user);
    AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
    return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
  }
}