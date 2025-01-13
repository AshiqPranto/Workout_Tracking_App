package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.UserDto;
import com.example.WorkoutTrackingApp.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    List<User> getAll();
    User findUserByEmail(String email);
    boolean checkIfEmailExists(String email);
    User findUserById(Long id);
    ResponseEntity<?> deleteUserById(Long id);
    UserDto updateUser(Long id, UserDto userDto);
}

