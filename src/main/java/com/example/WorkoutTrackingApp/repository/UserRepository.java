package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<List<User>> findAllByOrderByEmailAsc();
//    Optional<User> findById(Long id);
//    Optional<User> deleteUserById(Long id);

}
