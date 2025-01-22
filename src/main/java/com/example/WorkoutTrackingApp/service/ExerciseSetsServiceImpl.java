package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import com.example.WorkoutTrackingApp.repository.ExerciseSetsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseSetsServiceImpl implements ExerciseSetsService {

    private final ExerciseSetsRepository exerciseSetsRepository;

    @Override
    public ExerciseSets createExerciseSet(ExerciseSets exerciseSets) {
        return exerciseSetsRepository.save(exerciseSets);
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
    public ExerciseSets updateExerciseSet(Integer id, ExerciseSets updatedExerciseSet) {
        ExerciseSets existingExerciseSet = getExerciseSetById(id);
        existingExerciseSet.setReps(updatedExerciseSet.getReps());
        existingExerciseSet.setWeights(updatedExerciseSet.getWeights());
        existingExerciseSet.setExercise(updatedExerciseSet.getExercise());
        existingExerciseSet.setWorkout(updatedExerciseSet.getWorkout());
        return exerciseSetsRepository.save(existingExerciseSet);
    }

    @Override
    public void deleteExerciseSet(Integer id) {
        if (!exerciseSetsRepository.existsById(id)) {
            throw new EntityNotFoundException("ExerciseSet not found with id: " + id);
        }
        exerciseSetsRepository.deleteById(id);
    }
}