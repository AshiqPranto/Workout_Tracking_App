package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.ExerciseDTO;
import com.example.WorkoutTrackingApp.dto.ExerciseSetDTO;
import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExerciseSetsService {
    ResponseEntity<?> createExerciseSet(ExerciseSetDTO exerciseSetDTO);
    ExerciseSets getExerciseSetById(Integer id);
    List<ExerciseSets> getAllExerciseSets();
    List<ExerciseSets> getExerciseSetsByExerciseId(Integer exerciseId);
    List<ExerciseSets> getExerciseSetsByWorkoutId(Integer workoutId);
    List<ExerciseSets> getExerciseSetsByUserId(Integer userId);
    List<ExerciseSets> getAllByExerciseIdAndUserId(Integer exerciseId, Integer userId);
    ResponseEntity<?> updateExerciseSet(Integer id, ExerciseSetDTO exerciseSetDTO);
    void deleteExerciseSet(Integer id);
}