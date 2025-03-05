package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.dto.BodyMetricDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetric;
import com.example.WorkoutTrackingApp.service.BodyMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/body-metrics")
@RequiredArgsConstructor
public class BodyMetricsController {

    private final BodyMetricService bodyMetricService;

    @PostMapping
    public ResponseEntity<BodyMetric> createBodyMetrics(@RequestBody BodyMetricDTO bodyMetricDTO) {
        BodyMetric createdRecord = bodyMetricService.createBodyMetric(bodyMetricDTO);
        return ResponseEntity.ok(createdRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodyMetric> getBodyMetricsById(@PathVariable Integer id) {
        BodyMetric bodyMetric = bodyMetricService.getById(id);
        return new ResponseEntity<>(bodyMetric, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBodyMetrics(@PathVariable Integer id) {
        bodyMetricService.deleteBodyMetric(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/latest")
    public ResponseEntity<BodyMetric> getLatestBodyMetrics() {
        BodyMetric latestBodyMetric = bodyMetricService.getLatestBodyMetric();
        return new ResponseEntity<>(latestBodyMetric, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<BodyMetric>> getBodyMetricsHistory(
            @RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate
            ) {

        List<BodyMetric> history = bodyMetricService.getBodyMetricHistorybyDateRange(startDate, endDate);
        return ResponseEntity.ok(history);
    }
}
