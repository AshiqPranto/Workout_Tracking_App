package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BodyMetricsRepository extends JpaRepository<BodyMetrics, Integer> {
//    List<BodyMetrics> findByUserIdAndIsDeletedFalseOrderByTimestampDesc(Integer userId);
    Optional<BodyMetrics> findByIdAndIsDeletedFalse(Integer id);
}
