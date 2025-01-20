package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    List<Workout> findByUserId(Integer userId);
}