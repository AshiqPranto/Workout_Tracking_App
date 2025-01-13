package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.dto.UserDto;
import com.example.WorkoutTrackingApp.entity.User;
import com.example.WorkoutTrackingApp.service.UserServiceImp;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImp userService;

    @Autowired
    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User result = userService.findUserById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable String email) {
        User result = userService.findUserByEmail(email);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.checkIfEmailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/hello")
    public String returnHello() {
        return "Hello World";
    }


}