package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.UserUpdateDto;
import com.example.WorkoutTrackingApp.entity.User;
import com.example.WorkoutTrackingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Hash password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAllByOrderByEmailAsc().orElse(Collections.emptyList());
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public ResponseEntity<?> deleteUserById(Long id) {
        try{
            userRepository.deleteById(id);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Map.of("message: ", "Detele Successfully"), HttpStatus.OK)  ;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(new User());
    }

    public boolean checkIfEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserUpdateDto updateUser(Long id, UserUpdateDto userDto) {
        User user = userRepository.findById(id)
                .orElse(new User());

        // Update fields
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setIsActive(userDto.isActive());

        // Save updated user
        User updatedUser = userRepository.save(user);

        // Convert to DTO and return
        return mapToDto(updatedUser);
    }
    private UserUpdateDto mapToDto(User user) {
        UserUpdateDto dto = new UserUpdateDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.getIsActive());
        return dto;
    }
}
