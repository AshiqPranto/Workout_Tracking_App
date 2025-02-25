package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.entity.PersonalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {

    Optional<PersonalRecord> findByExerciseIdAndUserIdAndIsDeletedFalse(Integer workoutId, Integer userId);

    Integer user(User user);
}
