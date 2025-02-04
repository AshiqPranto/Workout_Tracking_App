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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseSetsServiceImpl implements ExerciseSetsService {

    private final ExerciseSetsRepository exerciseSetsRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;

    @Override
    public ResponseEntity<?> createExerciseSet(ExerciseSetDTO exerciseSetDTO) {
        try {
            ExerciseSets exerciseSets = convertToEntity(exerciseSetDTO);
            ExerciseSets savedExerciseSets = exerciseSetsRepository.save(exerciseSets);
            return new ResponseEntity<>(savedExerciseSets, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }


    }

    private ExerciseSets convertToEntity(ExerciseSetDTO exerciseSetDTO) {
        Exercise exercise = exerciseRepository.findById(exerciseSetDTO.getExerciseId()).get();
        Workout workout = workoutRepository.findById(exerciseSetDTO.getWorkoutId()).get();
        ExerciseSets exerciseSets = ExerciseSets.builder()
                .reps(exerciseSetDTO.getReps())
                .weights(exerciseSetDTO.getWeights())
                .exercise(exercise)
                .workout(workout)
                .build();
        return exerciseSets;
    }

    @Override
    public ExerciseSets getExerciseSetById(Integer id) {
        return exerciseSetsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ExerciseSet not found with id: " + id));
    }

    @Override
    public List<ExerciseSets> getAllExerciseSets() {
        return exerciseSetsRepository.findAll();
    }

    @Override
    public List<ExerciseSets> getExerciseSetsByExerciseId(Integer exerciseId) {
        return exerciseSetsRepository.findByExerciseId(exerciseId);
    }

    @Override
    public List<ExerciseSets> getExerciseSetsByWorkoutId(Integer workoutId) {
        return exerciseSetsRepository.findByWorkoutId(workoutId);
    }

    @Override
    public List<ExerciseSets> getExerciseSetsByUserId(Integer userId) {
        return exerciseSetsRepository.findAllByUserId(userId);
    }

    @Override
    public List<ExerciseSets> getAllByExerciseIdAndUserId(Integer exerciseId, Integer userId) {
        return exerciseSetsRepository.findAllByExerciseIdAndUserId(exerciseId, userId);
    }

    @Override
    public ResponseEntity<?> updateExerciseSet(Integer id, ExerciseSetDTO exerciseSetDTO) {
        try {
            ExerciseSets existingExerciseSet = getExerciseSetById(id);
            existingExerciseSet.setReps(exerciseSetDTO.getReps());
            existingExerciseSet.setWeights(exerciseSetDTO.getWeights());
            existingExerciseSet.setExercise(exerciseRepository.findById(exerciseSetDTO.getExerciseId()).get());
            existingExerciseSet.setWorkout(workoutRepository.findById(exerciseSetDTO.getWorkoutId()).get());
            ExerciseSets exerciseSets = exerciseSetsRepository.save(existingExerciseSet);
            return new ResponseEntity<>(exerciseSets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteExerciseSet(Integer id) {

        if (!exerciseSetsRepository.existsById(id)) {
            throw new EntityNotFoundException("ExerciseSet not found with id: " + id);
        }
        exerciseSetsRepository.deleteById(id);
    }
}