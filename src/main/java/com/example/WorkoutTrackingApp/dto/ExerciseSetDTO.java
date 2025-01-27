package com.example.WorkoutTrackingApp.dto;

import lombok.Data;

@Data
public class ExerciseSetDTO {
    private Integer reps;
    private Integer weights;
    private Integer exerciseId;
    private Integer workoutId;
}
