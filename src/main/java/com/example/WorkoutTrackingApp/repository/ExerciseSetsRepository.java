package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseSetsRepository extends JpaRepository<ExerciseSets, Integer> {

    List<ExerciseSets> findByExerciseId(Integer exerciseId);

    List<ExerciseSets> findByExerciseIdAndIsDeletedFalse(Integer exerciseId);

    List<ExerciseSets> findByWorkoutIdAndIsDeletedFalse(Integer workoutId);

    List<ExerciseSets> findAllByIsDeletedFalse();

    ExerciseSets findByIdAndIsDeletedFalse(Integer exerciseSetsId);

    @Query("SELECT es FROM ExerciseSets es WHERE es.workout.user.id = :userId AND es.isDeleted = false")
    List<ExerciseSets> findAllByUserId(@Param("userId") Integer userId);

    @Query("SELECT es FROM ExerciseSets es " +
            "WHERE es.exercise.id = :exerciseId AND es.workout.user.id = :userId AND es.isDeleted = false")
    List<ExerciseSets> findAllByExerciseIdAndUserId(@Param("exerciseId") Integer exerciseId,
                                                    @Param("userId") Integer userId);

}