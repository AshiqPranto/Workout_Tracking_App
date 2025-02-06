package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.ExerciseDTO;
import com.example.WorkoutTrackingApp.entity.Exercise;
import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import com.example.WorkoutTrackingApp.repository.ExerciseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ResponseEntity<?> createExercise(ExerciseDTO exerciseDTO) {
        log.info("Creating exercise with name {}", exerciseDTO.getName());
        try {
            Exercise exercise = convertToEntity(exerciseDTO);
            Exercise savedExercise = exerciseRepository.save(exercise);
            log.info("Exercise created successfully with ID: {}", savedExercise.getId());
            return new ResponseEntity<>(savedExercise, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating exercise: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred while creating exercise.", HttpStatus.INTERNAL_SERVER_ERROR);
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
        log.info("Fetching exercise with ID: {}", id);
        try {
            Optional<Exercise> exercise = exerciseRepository.findByIdAndIsDeletedFalse(id);
            if (exercise.isPresent()) {
                log.info("Exercise found with ID: {}", id);
                return new ResponseEntity<>(exercise.get(), HttpStatus.OK);
            } else {
                log.warn("Exercise not found with ID: {}", id);
                return new ResponseEntity<>("Exercise not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error fetching exercise with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>("Error fetching exercise", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Exercise> getAllExercises() {
        log.info("Fetching all exercises");
        return exerciseRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Exercise updateExercise(Integer id, ExerciseDTO exerciseDTO) {
        log.info("Updating exercise with ID: {}", id);
        Optional<Exercise> existingExerciseOpt = exerciseRepository.findById(id);

        if (existingExerciseOpt.isEmpty()) {
            log.warn("Exercise not found with ID: {}", id);
            throw new RuntimeException("Exercise not found");
        }
        log.info("Fetched exercise with ID {} successfully", id);
        Exercise existingExercise = existingExerciseOpt.get();
        existingExercise.setName(exerciseDTO.getName());
        existingExercise.setCategory(exerciseDTO.getCategory());
        existingExercise.setInstructions(exerciseDTO.getInstructions());
        existingExercise.setAnimationUrl(exerciseDTO.getAnimationUrl());
        existingExercise.setBodyPart(exerciseDTO.getBodyPart());

        Exercise updatedExercise = exerciseRepository.save(existingExercise);
        log.info("Exercise updated successfully with ID: {}", id);
        return updatedExercise;
    }

    //Todo: Handle transactional
    @Override
    public void deleteExercise(Integer id) {
        //TODO: Use doa
        log.info("Deleting exercise with ID: {}", id);
        Optional<Exercise> exerciseOpt = exerciseRepository.findById(id);

        if (exerciseOpt.isEmpty()) {
            log.warn("Exercise not found with ID: {}", id);
            throw new RuntimeException("Exercise not found");
        }

        Exercise exercise = exerciseOpt.get(); // Will throw an exception if not found
        exercise.setDeleted(true);
        exerciseRepository.save(exercise);
        log.info("Exercise soft deleted successfully with ID: {}", id);
    }
}