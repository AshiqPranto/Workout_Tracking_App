package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.dto.UpdateWorkoutDTO;
import com.example.WorkoutTrackingApp.dto.WorkoutDTO;
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
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutDTO workoutDTO) {
        ResponseEntity<?> responseEntity = workoutService.createWorkout(workoutDTO);
        return responseEntity;
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

    @GetMapping("/exercise/{exerciseId}")
    public ResponseEntity<List<Workout>> getWorkoutsByExerciseId(@PathVariable Integer exerciseId) {
        List<Workout> workouts = workoutService.getAllWorkoutsByExerciseId(exerciseId);
        return ResponseEntity.ok(workouts);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkout(
            @PathVariable Integer id,
            @RequestBody UpdateWorkoutDTO updateWorkoutDTO) {
        ResponseEntity<?> responseEntity = workoutService.updateWorkout(id, updateWorkoutDTO);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Integer id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}