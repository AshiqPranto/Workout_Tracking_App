package com.example.WorkoutTrackingApp.Mapper;

import com.example.WorkoutTrackingApp.dto.WorkoutDTO;
import com.example.WorkoutTrackingApp.entity.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkoutMapper {

    WorkoutMapper INSTANCE = Mappers.getMapper(WorkoutMapper.class);

    WorkoutDTO workoutToWorkoutDTO(Workout workout);
    @Mapping(target = "startTime", expression = "java(java.time.LocalDateTime.now())")  // Set current time
    @Mapping(target = "exerciseSets", ignore = true) // Ignore exerciseSets (empty list)
    Workout workoutDTOToWorkout(WorkoutDTO workoutDTO);
}
