package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.entity.Workout;

import java.util.List;

public interface WorkoutService {
    Workout createWorkout(Workout workout);
    Workout getWorkoutById(Integer id);
    List<Workout> getAllWorkouts();
    List<Workout> getWorkoutsByUserId(Integer userId);
    Workout updateWorkout(Integer id, Workout updatedWorkout);
    void deleteWorkout(Integer id);
}