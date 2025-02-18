package com.example.WorkoutTrackingApp.dto;

import com.example.WorkoutTrackingApp.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExerciseDTO {

    @NotBlank(message = ValidationMessages.NAME_REQUIRED)
    @Size(max = 2, message = ValidationMessages.NAME_MAX_LENGTH)
    private String name;

    @NotBlank(message = ValidationMessages.CATEGORY_REQUIRED)
    @Size(max = 50, message = ValidationMessages.CATEGORY_MAX_LENGTH)
    private String category;

    @Size(max = 5000, message = ValidationMessages.INSTRUCTIONS_MAX_LENGTH)
    private String instructions;

    @Size(max = 500, message = ValidationMessages.ANIMATION_URL_MAX_LENGTH)
    private String animationUrl;

    @NotBlank(message = ValidationMessages.BODY_PART_REQUIRED)
    @Size(max = 50, message = ValidationMessages.BODY_PART_MAX_LENGTH)
    private String bodyPart;
}
