package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.ExerciseDTO;
import com.example.WorkoutTrackingApp.entity.Exercise;
import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import com.example.WorkoutTrackingApp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ResponseEntity<?> createExercise(ExerciseDTO exerciseDTO) {
        try {
            Exercise exercise = convertToEntity(exerciseDTO);
            Exercise savedExercise = exerciseRepository.save(exercise);
            return new ResponseEntity<>(savedExercise, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private Exercise convertToEntity(ExerciseDTO exerciseDTO) {
        return Exercise.builder()
                .name(exerciseDTO.getName())
                .category(exerciseDTO.getCategory())
                .instructions(exerciseDTO.getInstructions())
                .animationUrl(exerciseDTO.getAnimationUrl())
                .bodyPart(exerciseDTO.getBodyPart())
                .exerciseSets(List.of())
                .build();
    }

    @Override
    public ResponseEntity<?> getExerciseById(Integer id) {
        try {
            Optional<Exercise> exercise = exerciseRepository.findByIdAndIsDeletedFalse(id);
            return new ResponseEntity<>(exercise.get(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Exercise updateExercise(Integer id, ExerciseDTO exerciseDTO) {
        Exercise existingExercise = exerciseRepository.findById(id).get(); // Will throw an exception if not found
        existingExercise.setName(exerciseDTO.getName());
        existingExercise.setCategory(exerciseDTO.getCategory());
        existingExercise.setInstructions(exerciseDTO.getInstructions());
        existingExercise.setAnimationUrl(exerciseDTO.getAnimationUrl());
        existingExercise.setBodyPart(exerciseDTO.getBodyPart());
        return exerciseRepository.save(existingExercise);
    }

    //Todo: Handle transactional
    @Override
    public void deleteExercise(Integer id) {
        //TODO: Use doa
        Exercise exercise = exerciseRepository.findById(id).get(); // Will throw an exception if not found
        exercise.setDeleted(true);
        exerciseRepository.save(exercise);
    }
}