package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.entity.Workout;
import com.example.WorkoutTrackingApp.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        return new ResponseEntity<>(workoutService.createWorkout(workout), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Integer id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Workout>> getWorkoutsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(
            @PathVariable Integer id,
            @RequestBody Workout updatedWorkout) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, updatedWorkout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Integer id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}