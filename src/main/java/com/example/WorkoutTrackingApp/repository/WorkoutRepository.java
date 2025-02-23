package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {


    List<Workout> findByUserIdAndIsDeletedFalse(Integer userId);

    List<Workout> findAllByIsDeletedFalse();

    Optional<Workout> findByIdAndIsDeletedFalse(Integer id);

    @Query("SELECT DISTINCT w FROM Workout w JOIN w.exerciseSets es WHERE es.exercise.id = :exerciseId AND es.workout.isDeleted = false")
    List<Workout> findAllWorkoutsByExerciseId(@Param("exerciseId") Integer exerciseId);

}