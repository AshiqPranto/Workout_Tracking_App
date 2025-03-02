package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyMetricsRepository extends JpaRepository<BodyMetrics, Integer> {
//    List<BodyMetrics> findByUserIdAndIsDeletedFalseOrderByTimestampDesc(Integer userId);
}
