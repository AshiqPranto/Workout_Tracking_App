package com.example.WorkoutTrackingApp.auth.controller;

import com.example.WorkoutTrackingApp.auth.dto.AuthenticationRequest;
import com.example.WorkoutTrackingApp.auth.dto.AuthenticationResponse;
import com.example.WorkoutTrackingApp.auth.dto.RegisterRequest;
import com.example.WorkoutTrackingApp.auth.service.AuthenticationServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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