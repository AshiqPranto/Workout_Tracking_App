package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.entity.Workout;
import com.example.WorkoutTrackingApp.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Override
    public Workout createWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public Workout getWorkoutById(Integer id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found with id: " + id));
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public List<Workout> getWorkoutsByUserId(Integer userId) {
        return workoutRepository.findByUserId(userId);
    }

    @Override
    public Workout updateWorkout(Integer id, Workout updatedWorkout) {
        Workout existingWorkout = getWorkoutById(id);
        existingWorkout.setName(updatedWorkout.getName());
        existingWorkout.setStartTime(updatedWorkout.getStartTime());
        existingWorkout.setEndTime(updatedWorkout.getEndTime());
        existingWorkout.setUser(updatedWorkout.getUser());
        return workoutRepository.save(existingWorkout);
    }

    @Override
    public void deleteWorkout(Integer id) {
        if (!workoutRepository.existsById(id)) {
            throw new EntityNotFoundException("Workout not found with id: " + id);
        }
        workoutRepository.deleteById(id);
    }
}