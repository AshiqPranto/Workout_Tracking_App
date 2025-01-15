package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.config.AuthenticationRequest;
import com.example.WorkoutTrackingApp.config.RegisterRequest;
import com.example.WorkoutTrackingApp.dto.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);
    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
