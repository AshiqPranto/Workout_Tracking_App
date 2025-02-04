package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.UpdateWorkoutDTO;
import com.example.WorkoutTrackingApp.dto.WorkoutDTO;
import com.example.WorkoutTrackingApp.entity.Workout;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WorkoutService {

    ResponseEntity<?> createWorkout(WorkoutDTO workoutDTO);

    Workout getWorkoutById(Integer id);

    List<Workout> getAllWorkouts();

    List<Workout> getWorkoutsByUserId(Integer userId);

    List<Workout> getAllWorkoutsByExerciseId(Integer exerciseId);

    ResponseEntity<?> updateWorkout(Integer id, UpdateWorkoutDTO updateWorkoutDTO);

    void deleteWorkout(Integer id);
}