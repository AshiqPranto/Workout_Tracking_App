package com.example.WorkoutTrackingApp.Mapper;

import com.example.WorkoutTrackingApp.dto.ExerciseDTO;
import com.example.WorkoutTrackingApp.entity.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExerciseMapper {

    ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

    ExerciseDTO exerciseToDTO(Exercise exercise);
    Exercise dtoToExercise(ExerciseDTO exerciseDTO);

}
