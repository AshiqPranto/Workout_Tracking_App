package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BodyMetricRepository extends JpaRepository<BodyMetric, Integer> {

    Optional<BodyMetric> findByIdAndIsDeletedFalse(Integer id);

    @Query("SELECT bm FROM BodyMetric bm WHERE bm.user.id = :userId ORDER BY bm.createdAt DESC LIMIT 1")
    Optional<BodyMetric> findLatestByUserId(Integer userId);

    @Query("SELECT NEW com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO(bm.weight, bm.createdAt) " +
            "FROM BodyMetric bm " +
            "WHERE bm.user.id = :userId " +
            "AND bm.isDeleted = false " +
            "AND bm.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY bm.createdAt DESC")
    List<BodyMetricHistoryDTO> findWeightHistory(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO(bm.height, bm.createdAt) " +
            "FROM BodyMetric bm " +
            "WHERE bm.user.id = :userId " +
            "AND bm.isDeleted = false " +
            "AND bm.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY bm.createdAt DESC")
    List<BodyMetricHistoryDTO> findHeightHistory(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO(bm.bodyFatPercentage, bm.createdAt) " +
            "FROM BodyMetric bm " +
            "WHERE bm.user.id = :userId " +
            "AND bm.isDeleted = false " +
            "AND bm.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY bm.createdAt DESC")
    List<BodyMetricHistoryDTO> findBodyFatPercentageHistory(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO(bm.muscleMass, bm.createdAt) " +
            "FROM BodyMetric bm " +
            "WHERE bm.user.id = :userId " +
            "AND bm.isDeleted = false " +
            "AND bm.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY bm.createdAt DESC")
    List<BodyMetricHistoryDTO> findMuscleMass(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO(bm.bmi, bm.createdAt) " +
            "FROM BodyMetric bm " +
            "WHERE bm.user.id = :userId " +
            "AND bm.isDeleted = false " +
            "AND bm.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY bm.createdAt DESC")
    List<BodyMetricHistoryDTO> findBMI(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO(bm.hipCircumference, bm.createdAt) " +
            "FROM BodyMetric bm " +
            "WHERE bm.user.id = :userId " +
            "AND bm.isDeleted = false " +
            "AND bm.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY bm.createdAt DESC")
    List<BodyMetricHistoryDTO> findHipCircumference(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO(bm.chestMeasurement, bm.createdAt) " +
            "FROM BodyMetric bm " +
            "WHERE bm.user.id = :userId " +
            "AND bm.isDeleted = false " +
            "AND bm.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY bm.createdAt DESC")
    List<BodyMetricHistoryDTO> findChestMeasurement(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
