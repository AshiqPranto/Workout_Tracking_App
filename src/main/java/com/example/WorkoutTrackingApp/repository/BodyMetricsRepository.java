package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BodyMetricsRepository extends JpaRepository<BodyMetrics, Integer> {
//    List<BodyMetrics> findByUserIdAndIsDeletedFalseOrderByTimestampDesc(Integer userId);
    Optional<BodyMetrics> findByIdAndIsDeletedFalse(Integer id);

    @Query("SELECT bm FROM BodyMetrics bm WHERE bm.user.id = :userId ORDER BY bm.createdAt DESC LIMIT 1")
    Optional<BodyMetrics> findLatestByUserId(Integer userId);

    List<BodyMetrics> findAllByUserIdAndIsDeletedFalseAndCreatedAtBetweenOrderByCreatedAtDesc(
            Integer userId, LocalDateTime startDate, LocalDateTime endDate
    );

    List<BodyMetrics> findAllByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Integer userId);
}
