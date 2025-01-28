package com.example.WorkoutTrackingApp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UpdateWorkoutDTO {
    private String name;
    private LocalDateTime endTime;
}
