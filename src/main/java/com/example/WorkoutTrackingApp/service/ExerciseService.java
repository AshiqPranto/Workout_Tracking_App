package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.ExerciseDTO;
import com.example.WorkoutTrackingApp.entity.Exercise;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExerciseService {

    Exercise createExercise(ExerciseDTO exerciseDTO);

    Exercise getExerciseById(Integer id);

    List<Exercise> getAllExercises();

    Exercise updateExercise(Integer id, ExerciseDTO exerciseDTO);

    void deleteExercise(Integer id);
}