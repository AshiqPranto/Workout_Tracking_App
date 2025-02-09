package com.example.WorkoutTrackingApp.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.WorkoutTrackingApp.dto.ExerciseSetDTO;
import com.example.WorkoutTrackingApp.entity.Exercise;
import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import com.example.WorkoutTrackingApp.entity.Workout;
import com.example.WorkoutTrackingApp.repository.ExerciseRepository;
import com.example.WorkoutTrackingApp.repository.ExerciseSetsRepository;
import com.example.WorkoutTrackingApp.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseSetsServiceImpl implements ExerciseSetsService {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseSetsServiceImpl.class);

    private final ExerciseSetsRepository exerciseSetsRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;

    @Override
    public ResponseEntity<?> createExerciseSet(ExerciseSetDTO exerciseSetDTO) {
        logger.info("Creating ExerciseSet with Exercise ID: {} and Workout ID: {}",
                exerciseSetDTO.getExerciseId(), exerciseSetDTO.getWorkoutId());
        try {
            ExerciseSets exerciseSets = convertToEntity(exerciseSetDTO);
            ExerciseSets savedExerciseSets = exerciseSetsRepository.save(exerciseSets);

            logger.debug("ExerciseSet created successfully with ID: {}", savedExerciseSets.getId());

            return new ResponseEntity<>(savedExerciseSets, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while creating ExerciseSet: {}", e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    private ExerciseSets convertToEntity(ExerciseSetDTO exerciseSetDTO) {
        logger.debug("Converting ExerciseSetDTO to ExerciseSets entity");

        Exercise exercise = exerciseRepository.findById(exerciseSetDTO.getExerciseId()).get();
        Workout workout = workoutRepository.findById(exerciseSetDTO.getWorkoutId()).get();
        ExerciseSets exerciseSets = ExerciseSets.builder()
                .reps(exerciseSetDTO.getReps())
                .weights(exerciseSetDTO.getWeights())
                .exercise(exercise)
                .workout(workout)
                .build();
        logger.debug("Conversion complete: {}", exerciseSets);
        return exerciseSets;
    }

    @Override
    public ExerciseSets getExerciseSetById(Integer id) {
        logger.info("Fetching ExerciseSet with ID: {}", id);

        ExerciseSets exerciseSets = null;
        try{
            exerciseSets = exerciseSetsRepository.findByIdAndIsDeletedFalse(id);
        }
        catch (EntityNotFoundException e){
            logger.error("EntityNotFoundException while fetching ExerciseSet with ID {}: {}", id, e.getMessage());

            throw new EntityNotFoundException("Exercise Sets not found..! May be deleted already..!");
        }
        catch (Exception e){
            logger.error("Unexpected error while fetching ExerciseSet with ID {}: {}", id, e.getMessage(), e);

            System.out.println(e.getMessage());
        }
        return exerciseSets;
    }

    @Override
    public List<ExerciseSets> getAllExerciseSets() {
        logger.info("Fetching all ExerciseSets that are not deleted");

        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findAllByIsDeletedFalse();
        logger.debug("Found {} ExerciseSets", exerciseSetsList.size());
        return exerciseSetsList;

    }

    @Override
    public List<ExerciseSets> getExerciseSetsByExerciseId(Integer exerciseId) {
        logger.info("Fetching ExerciseSets for Exercise ID: {}", exerciseId);
        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findByExerciseIdAndIsDeletedFalse(exerciseId);
        logger.debug("Found {} ExerciseSets for Exercise ID: {}", exerciseSetsList.size(), exerciseId);
        return exerciseSetsList;
    }

    @Override
    public List<ExerciseSets> getExerciseSetsByWorkoutId(Integer workoutId) {
        logger.info("Fetching ExerciseSets for Workout ID: {}", workoutId);
        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findByWorkoutIdAndIsDeletedFalse(workoutId);
        logger.debug("Found {} ExerciseSets for Workout ID: {}", exerciseSetsList.size(), workoutId);
        return exerciseSetsList;
    }

    @Override
    public List<ExerciseSets> getExerciseSetsByUserId(Integer userId) {
        logger.info("Fetching ExerciseSets for User ID: {}", userId);
        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findAllByUserId(userId);
        logger.debug("Found {} ExerciseSets for User ID: {}", exerciseSetsList.size(), userId);
        return exerciseSetsList;
    }

    @Override
    public List<ExerciseSets> getAllByExerciseIdAndUserId(Integer exerciseId, Integer userId) {
        logger.info("Fetching ExerciseSets for Exercise ID: {} and User ID: {}", exerciseId, userId);
        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findAllByExerciseIdAndUserId(exerciseId, userId);
        logger.debug("Found {} ExerciseSets for Exercise ID: {} and User ID: {}", exerciseSetsList.size(), exerciseId, userId);
        return exerciseSetsList;
    }

    @Override
    public ResponseEntity<?> updateExerciseSet(Integer id, ExerciseSetDTO exerciseSetDTO) {
        logger.info("Updating ExerciseSet with ID: {}", id);
        try {
            ExerciseSets existingExerciseSet = getExerciseSetById(id);
            existingExerciseSet.setReps(exerciseSetDTO.getReps());
            existingExerciseSet.setWeights(exerciseSetDTO.getWeights());
            existingExerciseSet.setExercise(exerciseRepository.findById(exerciseSetDTO.getExerciseId()).get());
            existingExerciseSet.setWorkout(workoutRepository.findById(exerciseSetDTO.getWorkoutId()).get());
            ExerciseSets updatedExerciseSet = exerciseSetsRepository.save(existingExerciseSet);
            logger.info("ExerciseSet updated successfully with ID: {}", updatedExerciseSet.getId());
            return new ResponseEntity<>(updatedExerciseSet, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating ExerciseSet with ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteExerciseSet(Integer id) {
        logger.info("Soft deleting ExerciseSet with ID: {}", id);
        if (!exerciseSetsRepository.existsById(id)) {
            logger.warn("ExerciseSet with ID {} not found for deletion", id);

            throw new EntityNotFoundException("ExerciseSet not found with id: " + id);
        }
        ExerciseSets exerciseSets = getExerciseSetById(id);
        exerciseSets.setDeleted(true);
        exerciseSetsRepository.save(exerciseSets);
        logger.info("ExerciseSet soft deleted successfully with ID: {}", id);
    }
}