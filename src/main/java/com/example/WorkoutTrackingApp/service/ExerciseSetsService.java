package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.entity.ExerciseSets;

import java.util.List;

public interface ExerciseSetsService {
    ExerciseSets createExerciseSet(ExerciseSets exerciseSets);
    ExerciseSets getExerciseSetById(Integer id);
    List<ExerciseSets> getAllExerciseSets();
    List<ExerciseSets> getExerciseSetsByExerciseId(Integer exerciseId);
    List<ExerciseSets> getExerciseSetsByWorkoutId(Integer workoutId);
    ExerciseSets updateExerciseSet(Integer id, ExerciseSets updatedExerciseSet);
    void deleteExerciseSet(Integer id);
}