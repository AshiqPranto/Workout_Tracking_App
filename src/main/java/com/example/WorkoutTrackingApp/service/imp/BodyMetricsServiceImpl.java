package com.example.WorkoutTrackingApp.service.imp;

import com.example.WorkoutTrackingApp.Mapper.BodyMetricsMapper;
import com.example.WorkoutTrackingApp.auth.service.UserService;
import com.example.WorkoutTrackingApp.dto.BodyMetricsDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.exception.BadRequestException;
import com.example.WorkoutTrackingApp.exception.ResourceNotFoundException;
import com.example.WorkoutTrackingApp.repository.BodyMetricsRepository;
import com.example.WorkoutTrackingApp.service.BodyMetricsService;
import com.example.WorkoutTrackingApp.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class BodyMetricsServiceImpl implements BodyMetricsService {

    private final BodyMetricsRepository bodyMetricsRepository;
    private final AuthUtil authUtil;
    private final UserService userService;
    private final BodyMetricsMapper bodyMetricsMapper = BodyMetricsMapper.INSTANCE;
    private final Integer currentUserId;

    public BodyMetricsServiceImpl(BodyMetricsRepository bodyMetricsRepository, AuthUtil authUtil, UserService userService) {
        this.bodyMetricsRepository = bodyMetricsRepository;
        this.authUtil = authUtil;
        this.userService = userService;
        currentUserId = authUtil.getAuthenticatedUserId();
    }

    public BodyMetrics createBodyMetrics(BodyMetricsDTO bodyMetricsDTO) {
        User user = userService.findUserById(currentUserId);

        BodyMetrics bodyMetrics = bodyMetricsMapper.bodyMetricsDtoToBodyMetrics(bodyMetricsDTO);
        bodyMetrics.setUser(user);

        return bodyMetricsRepository.save(bodyMetrics);
    }

    @Override
    public BodyMetrics getById(Integer id) {
        log.info("Get BodyMetrics by id: {}", id);
        BodyMetrics bodyMetrics = bodyMetricsRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("BodyMetrics with id " + id + " not found"));
        return bodyMetrics;
    }

    @Override
    public List<BodyMetrics> getBodyMetricsHistorybyDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Get BodyMetrics history by userId: {}", currentUserId);

        if (isDateRangeAbsent(startDate, endDate)) {
            log.info("Fetching All BodyMetrics history");
            return bodyMetricsRepository.findAllByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(currentUserId);
        }

        if(isStartDateAfterEndDate(startDate, endDate)) {
            throw new BadRequestException("Start date must be before end date");
        }

        log.info("Fetching BodyMetrics history by dateRange");
        return bodyMetricsRepository.findAllByUserIdAndIsDeletedFalseAndCreatedAtBetweenOrderByCreatedAtDesc(
                currentUserId, startDate, endDate
        );
    }

    private boolean isStartDateAfterEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.isAfter(endDate);
    }

    private static boolean isDateRangeAbsent(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate == null || endDate == null;
    }

    @Override
    public BodyMetrics getLatestBodyMetrics() {
        log.info("Get Latest BodyMetrics by id: {}", currentUserId);

        BodyMetrics bodyMetrics = bodyMetricsRepository.findLatestByUserId(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("BodyMetrics not found"));

        log.info("Got Latest BodyMetrics : {}", bodyMetrics);
        return bodyMetrics;
    }

    @Override
    public void deleteBodyMetrics(Integer id) {
        log.info("Delete BodyMetrics by id: {}", id);
        BodyMetrics bodyMetrics = getById(id);
        bodyMetrics.setDeleted(true);

        bodyMetricsRepository.save(bodyMetrics);
        log.info("BodyMetrics with id {} has been deleted", id);
    }
}
