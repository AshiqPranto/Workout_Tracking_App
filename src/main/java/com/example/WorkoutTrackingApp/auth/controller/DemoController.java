package com.example.WorkoutTrackingApp.auth.controller;

import com.example.WorkoutTrackingApp.auth.service.AuthenticationServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DemoController {

    private final AuthenticationServiceImp service;

    @GetMapping("/user")
    public ResponseEntity<?> register( ) {
        return  new ResponseEntity<>("User only can access this service", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> adminEndpoint() {
        return new ResponseEntity<>("admin can access this page", HttpStatus.OK);
    }

    @GetMapping("/userandadmin")
    public ResponseEntity<?> userAndAdminEndpoint() {
        return new ResponseEntity<>("user and admin both can access this page", HttpStatus.OK);
    }
}