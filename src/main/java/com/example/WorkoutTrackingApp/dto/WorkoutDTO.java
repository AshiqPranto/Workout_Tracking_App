package com.example.WorkoutTrackingApp.dto;

import com.example.WorkoutTrackingApp.constants.ValidationMessages;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutDTO {

    @NotBlank(message = ValidationMessages.WORKOUT_NAME_REQUIRED)
    @Column(nullable = false, length = 100)
    private String name;

}
