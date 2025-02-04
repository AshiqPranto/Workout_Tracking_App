package com.example.WorkoutTrackingApp.auth.service;

import com.example.WorkoutTrackingApp.auth.dto.AuthenticationRequest;
import com.example.WorkoutTrackingApp.auth.dto.AuthenticationResponse;
import com.example.WorkoutTrackingApp.auth.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
