package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.dto.BodyMetricsDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import com.example.WorkoutTrackingApp.service.BodyMetricsService;
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

    private final BodyMetricsService bodyMetricsService;

    @PostMapping
    public ResponseEntity<BodyMetrics> createBodyMetrics(@RequestBody BodyMetricsDTO bodyMetricsDTO) {
        BodyMetrics createdRecord = bodyMetricsService.createBodyMetrics(bodyMetricsDTO);
        return ResponseEntity.ok(createdRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodyMetrics> getBodyMetricsById(@PathVariable Integer id) {
        BodyMetrics bodyMetrics = bodyMetricsService.getById(id);
        return new ResponseEntity<>(bodyMetrics, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBodyMetrics(@PathVariable Integer id) {
        bodyMetricsService.deleteBodyMetrics(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/latest")
    public ResponseEntity<BodyMetrics> getLatestBodyMetrics() {
        BodyMetrics latestBodyMetrics = bodyMetricsService.getLatestBodyMetrics();
        return new ResponseEntity<>(latestBodyMetrics, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<BodyMetrics>> getBodyMetricsHistory(
            @RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate
            ) {

        List<BodyMetrics> history = bodyMetricsService.getBodyMetricsHistorybyDateRange(startDate, endDate);
        return ResponseEntity.ok(history);
    }
}
