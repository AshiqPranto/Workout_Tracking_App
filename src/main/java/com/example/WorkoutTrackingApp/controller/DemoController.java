package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.config.AuthenticationRequest;
import com.example.WorkoutTrackingApp.config.RegisterRequest;
import com.example.WorkoutTrackingApp.config.SecurityConfiguration;
import com.example.WorkoutTrackingApp.dto.AuthenticationResponse;
import com.example.WorkoutTrackingApp.service.AuthenticationServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DemoController {

    private final AuthenticationServiceImp service;

    @GetMapping("/demo")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> register( ) {
        var x = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>("Demo", HttpStatus.OK);
    }

    @GetMapping("/debug")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> debugEndpoint() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(Map.of(
                "Authorities", authentication.getAuthorities(),
                "Principal", authentication.getPrincipal(),
                "Details", authentication.getDetails()
        ));
    }


}