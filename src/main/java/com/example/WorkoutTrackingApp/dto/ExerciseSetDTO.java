package com.example.WorkoutTrackingApp.dto;

import com.example.WorkoutTrackingApp.constants.ValidationMessages;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExerciseSetDTO {

    @Min(value = 1, message = ValidationMessages.REPS_MIN_Number)
    @Column(nullable = false)
    private Integer reps;

    @Min(value = 0, message = ValidationMessages.WEIGHTS_MIN)
    @Column(nullable = false)
    private Integer weights;

    @NotNull(message = "Exercise ID is required")
    @Min(value = 1, message = "Exercise ID must be a positive number")
    private Integer exerciseId;

    @NotNull(message = "Workout ID is required")
    @Min(value = 1, message = "Workout ID must be a positive number")
    private Integer workoutId;
}
