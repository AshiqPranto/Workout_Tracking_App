package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.dto.BodyMetricsDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BodyMetricsService {

    public BodyMetrics createBodyMetrics(BodyMetricsDTO bodyMetricsDTO);

    public BodyMetrics getById(Integer id);

    public void deleteBodyMetrics(Integer id);

    public List<BodyMetricsDTO> getBodyMetricsHistory();

}
