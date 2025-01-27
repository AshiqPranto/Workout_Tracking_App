package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.dto.ExerciseDTO;
import com.example.WorkoutTrackingApp.entity.Exercise;
import com.example.WorkoutTrackingApp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping()
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createExercise(@RequestBody ExerciseDTO exerciseDTO) {

        ResponseEntity<?> responseEntity = exerciseService.createExercise(exerciseDTO);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExerciseById(@PathVariable Integer id) {
        ResponseEntity<?> responseEntity = exerciseService.getExerciseById(id);
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Exercise> updateExercise(
            @PathVariable Integer id, 
            @RequestBody ExerciseDTO exerciseDTO) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, exerciseDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteExercise(@PathVariable Integer id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}