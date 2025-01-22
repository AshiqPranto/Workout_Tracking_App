package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.entity.ExerciseSets;

import java.util.List;

public interface ExerciseSetsService {
    ExerciseSets createExerciseSet(ExerciseSets exerciseSets);
    ExerciseSets getExerciseSetById(Integer id);
    List<ExerciseSets> getAllExerciseSets();
    List<ExerciseSets> getExerciseSetsByExerciseId(Integer exerciseId);
    List<ExerciseSets> getExerciseSetsByWorkoutId(Integer workoutId);
    List<ExerciseSets> getExerciseSetsByUserId(Integer userId);
    List<ExerciseSets> getAllByExerciseIdAndUserId(Integer exerciseId, Integer userId);
    ExerciseSets updateExerciseSet(Integer id, ExerciseSets updatedExerciseSet);
    void deleteExerciseSet(Integer id);
}