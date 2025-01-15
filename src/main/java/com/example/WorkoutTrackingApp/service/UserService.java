package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.UserUpdateDto;
import com.example.WorkoutTrackingApp.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    List<User> getAll();
    User findUserByEmail(String email);
    boolean checkIfEmailExists(String email);
    User findUserById(Long id);
    ResponseEntity<?> deleteUserById(Long id);
    UserUpdateDto updateUser(Long id, UserUpdateDto userDto);
}

