package com.example.WorkoutTrackingApp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BodyMetricHistoryDTO {
    private Double value;
    private LocalDateTime createdAt;

    public BodyMetricHistoryDTO(Double value, LocalDateTime createdAt) {
        this.value = value;
        this.createdAt = createdAt;
    }
}
