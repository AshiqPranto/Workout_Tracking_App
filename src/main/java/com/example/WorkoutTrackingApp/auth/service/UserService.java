package com.example.WorkoutTrackingApp.auth.service;

import com.example.WorkoutTrackingApp.auth.dto.UserUpdateDto;
import com.example.WorkoutTrackingApp.auth.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    List<User> getAll();

    User findUserByEmail(String email);

    boolean checkIfEmailExists(String email);

    User findUserById(Integer id);

    void deleteUserById(Integer id);

    UserUpdateDto updateUser(Integer id, UserUpdateDto userDto);
}

