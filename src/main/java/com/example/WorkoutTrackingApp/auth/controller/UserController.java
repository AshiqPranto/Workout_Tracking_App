package com.example.WorkoutTrackingApp.auth.controller;

//import jakarta.validation.Valid;
import com.example.WorkoutTrackingApp.auth.dto.UserUpdateDto;
import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.auth.service.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User result = userService.findUserById(id);
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
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDto> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateDto userDto) {
        UserUpdateDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/hello")
    public String returnHello() {
        return "Hello World";
    }


}