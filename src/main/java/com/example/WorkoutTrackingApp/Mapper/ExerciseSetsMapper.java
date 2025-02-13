package com.example.WorkoutTrackingApp.Mapper;

import com.example.WorkoutTrackingApp.dto.ExerciseSetDTO;
import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import org.mapstruct.factory.Mappers;

public interface ExerciseSetsMapper {

    ExerciseSetsMapper INSTANCE = Mappers.getMapper(ExerciseSetsMapper.class);

    ExerciseSetDTO ExerciseSetsToDTO(ExerciseSets exerciseSets);
    ExerciseSets ExerciseSetDTOToExerciseSets(ExerciseSetDTO exerciseSetDTO);

}
