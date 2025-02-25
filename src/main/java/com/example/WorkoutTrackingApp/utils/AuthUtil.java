package com.example.WorkoutTrackingApp.utils;

import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    public static String getAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public Integer getAuthenticatedUserId() {
        String userEmail = getAuthenticatedUserName();
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
            return user.getId();
        }
        throw new RuntimeException("User is not authenticated");
    }
}
