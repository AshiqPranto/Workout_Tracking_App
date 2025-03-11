package com.example.WorkoutTrackingApp.service.imp;

import com.example.WorkoutTrackingApp.Mapper.BodyMetricMapper;
import com.example.WorkoutTrackingApp.auth.service.UserService;
import com.example.WorkoutTrackingApp.dto.BodyMetricDTO;
import com.example.WorkoutTrackingApp.dto.BodyMetricHistoryDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetric;
import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.exception.BadRequestException;
import com.example.WorkoutTrackingApp.exception.ResourceNotFoundException;
import com.example.WorkoutTrackingApp.repository.BodyMetricRepository;
import com.example.WorkoutTrackingApp.service.BodyMetricService;
import com.example.WorkoutTrackingApp.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class BodyMetricServiceImpl implements BodyMetricService {

    private final BodyMetricRepository bodyMetricRepository;
    private final AuthUtil authUtil;
    private final UserService userService;
    private final BodyMetricMapper bodyMetricMapper = BodyMetricMapper.INSTANCE;
    private static final Set<String> VALID_FIELDS = new HashSet<>(Arrays.asList(
            "weight", "height", "bodyFatPercentage", "muscleMass", "bmi", "hipCircumference", "chestMeasurement"
    ));

    public BodyMetricServiceImpl(BodyMetricRepository bodyMetricRepository, AuthUtil authUtil, UserService userService) {
        this.bodyMetricRepository = bodyMetricRepository;
        this.authUtil = authUtil;
        this.userService = userService;
    }

    public BodyMetric createBodyMetric(BodyMetricDTO bodyMetricDTO) {
        Integer currentUserId = authUtil.getAuthenticatedUserId();
        User user = userService.findUserById(currentUserId);

        BodyMetric bodyMetric = bodyMetricMapper.bodyMetricDtoToBodyMetric(bodyMetricDTO);
        bodyMetric.setUser(user);

        return bodyMetricRepository.save(bodyMetric);
    }

    @Override
    public BodyMetric getById(Integer id) {
        log.info("Get BodyMetric by id: {}", id);
        BodyMetric bodyMetric = bodyMetricRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("BodyMetric with id " + id + " not found"));
        return bodyMetric;
    }

    @Override
    public List<BodyMetricHistoryDTO> getBodyMetricHistory(String field, LocalDateTime startDate, LocalDateTime endDate) {
        Integer currentUserId = authUtil.getAuthenticatedUserId();
        log.info("Get BodyMetric history by userId: {}", currentUserId);

        validateField(field);
        validateDateRange(startDate, endDate);

        log.info("Fetching BodyMetric history for field: {} in date range", field);
        return fetchHistory(currentUserId, field, startDate, endDate);
    }

    private List<BodyMetricHistoryDTO> fetchHistory(Integer currentUserId, String field, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching BodyMetric history for field: {} in date range", field);
        switch (field) {
            case "weight":
                return bodyMetricRepository.findWeightHistory(currentUserId, startDate, endDate);
            case "height":
                return bodyMetricRepository.findHeightHistory(currentUserId, startDate, endDate);
            case "bodyFatPercentage":
                return bodyMetricRepository.findBodyFatPercentageHistory(currentUserId, startDate, endDate);
            case "muscleMass":
                return bodyMetricRepository.findMuscleMass(currentUserId, startDate, endDate);
            case "bmi":
                return bodyMetricRepository.findBMI(currentUserId, startDate, endDate);
            case "hipCircumference":
                return bodyMetricRepository.findHipCircumference(currentUserId, startDate, endDate);
            case "chestMeasurement":
                return bodyMetricRepository.findChestMeasurement(currentUserId, startDate, endDate);
            default:
                return Collections.emptyList();
        }
    }

    private void validateField(String field) {
        if (field == null || field.isBlank()) {
            throw new BadRequestException("Field parameter is required");
        }

        if(!VALID_FIELDS.contains(field)) {
            throw new BadRequestException("Field parameter is invalid");
        }
    }

    private void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate == null || endDate == null) {
            throw new BadRequestException("Both Start date and end date are required");
        }

        if(startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date must be before the end date");
        }
    }

    @Override
    public BodyMetric getLatestBodyMetric() {
        Integer currentUserId = authUtil.getAuthenticatedUserId();
        log.info("Get Latest BodyMetric by id: {}", currentUserId);

        BodyMetric bodyMetric = bodyMetricRepository.findLatestByUserId(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("No BodyMetric records found for the user"));

        log.info("Got Latest BodyMetric with id: {}", bodyMetric.getId());
        return bodyMetric;
    }

    @Override
    public void deleteBodyMetric(Integer id) {
        log.info("Delete BodyMetric by id: {}", id);
        BodyMetric bodyMetric = getById(id);
        bodyMetric.setDeleted(true);

        bodyMetricRepository.save(bodyMetric);
        log.info("BodyMetric with id {} has been deleted", id);
    }
}
