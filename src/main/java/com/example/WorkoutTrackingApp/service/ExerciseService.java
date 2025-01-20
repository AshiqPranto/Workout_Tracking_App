package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.entity.Exercise;

import java.util.List;

public interface ExerciseService {
    Exercise createExercise(Exercise exercise);

    Exercise getExerciseById(Integer id);

    List<Exercise> getAllExercises();

    Exercise updateExercise(Integer id, Exercise exercise);

    void deleteExercise(Integer id);
}