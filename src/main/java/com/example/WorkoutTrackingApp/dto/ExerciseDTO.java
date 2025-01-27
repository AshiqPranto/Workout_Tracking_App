package com.example.WorkoutTrackingApp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExerciseDTO {
    private String name;
    private String category;
    private String instructions;
    private String animationUrl;
    private String bodyPart;
}
