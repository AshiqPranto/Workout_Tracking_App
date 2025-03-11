package com.example.WorkoutTrackingApp.Mapper;

import com.example.WorkoutTrackingApp.dto.BodyMetricDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BodyMetricMapper {

    BodyMetricMapper INSTANCE = Mappers.getMapper(BodyMetricMapper.class);

    BodyMetric bodyMetricDtoToBodyMetric(BodyMetricDTO bodyMetricDTO);
    BodyMetricDTO bodyMetricToBodyMetricDTO(BodyMetric bodyMetric);

}
