package com.example.WorkoutTrackingApp.Mapper;

import com.example.WorkoutTrackingApp.dto.WorkoutDTO;
import com.example.WorkoutTrackingApp.entity.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkoutMapper {

    WorkoutMapper INSTANCE = Mappers.getMapper(WorkoutMapper.class);

    Workout dtoToWorkout(WorkoutDTO workoutDTO);
    WorkoutDTO workoutToWorkoutDTO(Workout workout);

}
