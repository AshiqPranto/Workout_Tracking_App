package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.entity.Exercise;
import com.example.WorkoutTrackingApp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise getExerciseById(Integer id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found with ID: " + id));
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise updateExercise(Integer id, Exercise exercise) {
        Exercise existingExercise = getExerciseById(id); // Will throw an exception if not found
        existingExercise.setName(exercise.getName());
        existingExercise.setCategory(exercise.getCategory());
        existingExercise.setInstructions(exercise.getInstructions());
        existingExercise.setAnimationUrl(exercise.getAnimationUrl());
        existingExercise.setBodyPart(exercise.getBodyPart());
        return exerciseRepository.save(existingExercise);
    }

    @Override
    public void deleteExercise(Integer id) {
        Exercise exercise = getExerciseById(id); // Will throw an exception if not found
        exerciseRepository.delete(exercise);
    }
}