package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    List<Workout> findByUserId(Integer userId);

    @Query("SELECT DISTINCT w FROM Workout w JOIN w.exerciseSets es WHERE es.exercise.id = :exerciseId")
    List<Workout> findAllWorkoutsByExerciseId(@Param("exerciseId") Integer exerciseId);

}