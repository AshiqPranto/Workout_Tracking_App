package com.example.WorkoutTrackingApp.entity;

import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "body_metrics")
public class BodyMetrics extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

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
