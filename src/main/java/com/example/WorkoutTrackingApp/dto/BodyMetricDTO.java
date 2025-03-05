package com.example.WorkoutTrackingApp.dto;

import com.example.WorkoutTrackingApp.constants.ValidationMessages;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BodyMetricDTO {
    @NotNull(message = ValidationMessages.WEIGHT_NOT_NULL)
    @Positive(message = ValidationMessages.WEIGHT_POSITIVE)
    private Double weight;

    @NotNull(message = ValidationMessages.HEIGHT_NOT_NULL)
    @Positive(message = ValidationMessages.HEIGHT_POSITIVE)
    private Double height;

    @Min(value = 0, message = ValidationMessages.BODY_FAT_PERCENTAGE_MIN)
    @Max(value = 100, message = ValidationMessages.BODY_FAT_PERCENTAGE_MAX)
    private Double bodyFatPercentage;

    @PositiveOrZero(message = ValidationMessages.MUSCLE_MASS_ZERO_OR_POSITIVE)
    private Double muscleMass;

    @PositiveOrZero(message = ValidationMessages.BMI_ZERO_OR_POSITIVE)
    private Double bmi;

    @PositiveOrZero(message = ValidationMessages.HIP_CIRCUMFERENCE_ZERO_OR_POSITIVE)
    private Double hipCircumference;

    @PositiveOrZero(message = ValidationMessages.CHEST_MEASUREMENT_ZERO_OR_POSITIVE)
    private Double chestMeasurement;
}
