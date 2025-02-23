package com.example.WorkoutTrackingApp.auth.service;

import com.example.WorkoutTrackingApp.auth.dto.UserUpdateDto;
import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.auth.repository.UserRepository;
import com.example.WorkoutTrackingApp.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        log.info("Registering new user: {}", user.getEmail());
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    public List<User> getAll() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAllByIsDeletedFalseOrderByEmailAsc().orElse(Collections.emptyList());
        log.info("Fetched {} users", users.size());
        return users;
    }

    public User findUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        return userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Deleting user by ID: {}", id);

            User user = userRepository.findByIdAndIsDeletedFalse(id)
                            .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
            user.setDeleted(true);
            userRepository.save(user);
            log.info("User soft deleted successfully with ID: {}", id);
    }

    public User findUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
//        return userRepository.findByEmail(email).orElse(new User());
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
        return user;
    }

    public boolean checkIfEmailExists(String email) {
        log.debug("Checking if email exists: {}", email);
        boolean exists = userRepository.findByEmailAndIsDeletedFalse(email).isPresent();
        log.debug("Email exists: {}", exists);
        return exists;
    }

    public UserUpdateDto updateUser(Long id, UserUpdateDto userDto) {
        log.info("Updating user with ID: {}", id);
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setActive(userDto.isActive());
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully: ID: {}, Name: {}", updatedUser.getId(), updatedUser.getName());
        return mapToDto(updatedUser);
    }

    private UserUpdateDto mapToDto(User user) {
        log.debug("Mapping User entity to DTO: {}", user.getId());
        UserUpdateDto dto = new UserUpdateDto();
        dto.setId((int)user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.getIsActive());
        return dto;
    }
}
