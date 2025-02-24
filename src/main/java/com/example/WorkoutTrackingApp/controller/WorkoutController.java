package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.dto.UpdateWorkoutDTO;
import com.example.WorkoutTrackingApp.dto.WorkoutDTO;
import com.example.WorkoutTrackingApp.entity.Workout;
import com.example.WorkoutTrackingApp.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<?> createWorkout(@Valid @RequestBody WorkoutDTO workoutDTO) {
        Workout createdWorkout = workoutService.createWorkout(workoutDTO);
        return ResponseEntity.status(201).body(createdWorkout);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Integer id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<List<Workout>> getWorkoutsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUserId(userId));
    }

    @GetMapping("/exercise/{exerciseId}")
    public ResponseEntity<List<Workout>> getWorkoutsByExerciseId(@PathVariable Integer exerciseId) {
        List<Workout> workouts = workoutService.getAllWorkoutsByExerciseId(exerciseId);
        return ResponseEntity.ok(workouts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkout(@Valid @PathVariable Integer id, @RequestBody UpdateWorkoutDTO updateWorkoutDTO) {
        Workout updatedWorkout = workoutService.updateWorkout(id, updateWorkoutDTO);
        return ResponseEntity.ok(updatedWorkout);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Integer id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/end-workout/{id}")
    public ResponseEntity<Void> endWorkout(@PathVariable Integer id) {
        workoutService.endWorkout(id);
        return ResponseEntity.noContent().build();
    }
}