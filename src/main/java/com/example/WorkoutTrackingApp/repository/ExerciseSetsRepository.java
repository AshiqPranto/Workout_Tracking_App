package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseSetsRepository extends JpaRepository<ExerciseSets, Integer> {
    List<ExerciseSets> findByExerciseId(Integer exerciseId);
    List<ExerciseSets> findByWorkoutId(Integer workoutId);
}