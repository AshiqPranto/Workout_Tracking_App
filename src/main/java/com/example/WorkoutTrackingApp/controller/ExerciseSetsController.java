package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import com.example.WorkoutTrackingApp.service.ExerciseSetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise-sets")
@RequiredArgsConstructor
public class ExerciseSetsController {

    private final ExerciseSetsService exerciseSetsService;

    @PostMapping
    public ResponseEntity<ExerciseSets> createExerciseSet(@RequestBody ExerciseSets exerciseSets) {
        return new ResponseEntity<>(exerciseSetsService.createExerciseSet(exerciseSets), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseSets> getExerciseSetById(@PathVariable Integer id) {
        return ResponseEntity.ok(exerciseSetsService.getExerciseSetById(id));
    }

    @GetMapping
    public ResponseEntity<List<ExerciseSets>> getAllExerciseSets() {
        return ResponseEntity.ok(exerciseSetsService.getAllExerciseSets());
    }

    @GetMapping("/exercise/{exerciseId}")
    public ResponseEntity<List<ExerciseSets>> getExerciseSetsByExerciseId(@PathVariable Integer exerciseId) {
        return ResponseEntity.ok(exerciseSetsService.getExerciseSetsByExerciseId(exerciseId));
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<List<ExerciseSets>> getExerciseSetsByWorkoutId(@PathVariable Integer workoutId) {
        return ResponseEntity.ok(exerciseSetsService.getExerciseSetsByWorkoutId(workoutId));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ExerciseSets>> getExerciseSetsByUserId(@PathVariable Integer id) {
        return new ResponseEntity<>(exerciseSetsService.getExerciseSetsByUserId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseSets> updateExerciseSet(
            @PathVariable Integer id,
            @RequestBody ExerciseSets updatedExerciseSet) {
        return ResponseEntity.ok(exerciseSetsService.updateExerciseSet(id, updatedExerciseSet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseSet(@PathVariable Integer id) {
        exerciseSetsService.deleteExerciseSet(id);
        return ResponseEntity.noContent().build();
    }
}