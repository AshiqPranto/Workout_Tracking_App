package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.BodyMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BodyMetricRepository extends JpaRepository<BodyMetric, Integer> {

    Optional<BodyMetric> findByIdAndIsDeletedFalse(Integer id);

    @Query("SELECT bm FROM BodyMetric bm WHERE bm.user.id = :userId ORDER BY bm.createdAt DESC LIMIT 1")
    Optional<BodyMetric> findLatestByUserId(Integer userId);

    List<BodyMetric> findAllByUserIdAndIsDeletedFalseAndCreatedAtBetweenOrderByCreatedAtDesc(
            Integer userId, LocalDateTime startDate, LocalDateTime endDate
    );

    List<BodyMetric> findAllByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Integer userId);
}
