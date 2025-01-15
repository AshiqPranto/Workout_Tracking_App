package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.Enum.Role;
import com.example.WorkoutTrackingApp.config.AuthenticationRequest;
import com.example.WorkoutTrackingApp.config.JwtService;
import com.example.WorkoutTrackingApp.config.RegisterRequest;
import com.example.WorkoutTrackingApp.dto.AuthenticationResponse;
import com.example.WorkoutTrackingApp.entity.User;
import com.example.WorkoutTrackingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
//    var user = User.builder()
//        .name(request.getName())
//        .email(request.getEmail())
//        .password(passwordEncoder.encode(request.getPassword()))
//        .role(Role.USER)
//        .build();
    repository.save(user);
    var jwtToken = jwtService.generateToken(user);
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
    var jwtToken = jwtService.generateToken(user);
    AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
    return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
  }
}