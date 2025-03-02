package com.example.WorkoutTrackingApp.Mapper;

import com.example.WorkoutTrackingApp.dto.BodyMetricsDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BodyMetricsMapper {

    BodyMetricsMapper INSTANCE = Mappers.getMapper(BodyMetricsMapper.class);

    BodyMetrics bodyMetricsDtoToBodyMetrics(BodyMetricsDTO bodyMetricsDTO);
    BodyMetricsDTO bodyMetricsToBodyMetricsDTO(BodyMetrics bodyMetrics);

}
