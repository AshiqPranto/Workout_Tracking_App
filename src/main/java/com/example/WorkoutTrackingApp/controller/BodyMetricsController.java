package com.example.WorkoutTrackingApp.controller;

import com.example.WorkoutTrackingApp.dto.BodyMetricsDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import com.example.WorkoutTrackingApp.service.BodyMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<List<BodyMetricsDTO>> getBodyMetricsHistory() {
        List<BodyMetricsDTO> history = bodyMetricsService.getBodyMetricsHistory();
        return ResponseEntity.ok(history);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBodyMetrics(@PathVariable Integer id) {
        bodyMetricsService.deleteBodyMetrics(id);
        return ResponseEntity.ok("Body Metrics entry deleted successfully.");
    }
}
