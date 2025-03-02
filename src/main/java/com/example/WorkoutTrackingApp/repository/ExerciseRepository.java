package com.example.WorkoutTrackingApp.repository;

import com.example.WorkoutTrackingApp.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    List<Exercise> findAllByIsDeletedFalse();

    Optional<Exercise> findByIdAndIsDeletedFalse(Integer id);

}