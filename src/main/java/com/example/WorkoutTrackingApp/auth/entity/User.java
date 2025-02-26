package com.example.WorkoutTrackingApp.auth.entity;

import com.example.WorkoutTrackingApp.auth.Enum.Role;
import com.example.WorkoutTrackingApp.entity.BaseEntity;
import com.example.WorkoutTrackingApp.entity.PersonalRecord;
import com.example.WorkoutTrackingApp.entity.Workout;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User extends BaseEntity implements UserDetails {

    private String name;

    private String email;

    private String password;

    public String getPassword() {
        return password;
    }

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // Default role

    @Column(name = "is_active")
    private boolean isActive = true; // For enabling/disabling user accounts

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Workout> workouts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PersonalRecord> personalRecords = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean getIsActive() {
        return isActive;
    }

    private LocalDateTime updatedAt;

}
