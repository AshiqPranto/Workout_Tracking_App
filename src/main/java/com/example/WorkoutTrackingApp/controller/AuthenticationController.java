package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.config.AuthenticationRequest;
import com.example.WorkoutTrackingApp.config.RegisterRequest;
import com.example.WorkoutTrackingApp.dto.AuthenticationResponse;
import com.example.WorkoutTrackingApp.service.AuthenticationServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationServiceImp service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return service.register(request);
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return service.authenticate(request);
  }


}