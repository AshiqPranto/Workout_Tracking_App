package com.example.WorkoutTrackingApp.entity;

import com.example.WorkoutTrackingApp.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "exercise_sets")
public class ExerciseSets extends BaseEntity {

    @Min(value = 1, message = ValidationMessages.REPS_MIN_Number)
    @Column(nullable = false)
    private Integer reps;

    @Min(value = 0, message = ValidationMessages.WEIGHTS_MIN)
    @Column(nullable = false)
    private Integer weights;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonBackReference
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonBackReference
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

}