package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.BodyMetricDTO;
import com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetric;

import java.time.LocalDateTime;
import java.util.List;

public interface BodyMetricService {

    public BodyMetric createBodyMetric(BodyMetricDTO bodyMetricDTO);

    public BodyMetric getById(Integer id);

    public void deleteBodyMetric(Integer id);

    public List<BodyMetricHistoryDTO> getBodyMetricHistory(
            String field, LocalDateTime startDate, LocalDateTime endDate
    );

    public BodyMetric getLatestBodyMetric();

}
