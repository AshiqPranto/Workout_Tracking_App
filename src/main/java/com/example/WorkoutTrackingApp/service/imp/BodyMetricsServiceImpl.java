package com.example.WorkoutTrackingApp.service.imp;

import com.example.WorkoutTrackingApp.Mapper.BodyMetricsMapper;
import com.example.WorkoutTrackingApp.auth.service.UserService;
import com.example.WorkoutTrackingApp.dto.BodyMetricsDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.exception.ResourceNotFoundException;
import com.example.WorkoutTrackingApp.repository.BodyMetricsRepository;
import com.example.WorkoutTrackingApp.service.BodyMetricsService;
import com.example.WorkoutTrackingApp.utils.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class BodyMetricsServiceImpl implements BodyMetricsService {

    private final BodyMetricsRepository bodyMetricsRepository;
    private final AuthUtil authUtil;
    private final UserService userService;
    BodyMetricsMapper bodyMetricsMapper = BodyMetricsMapper.INSTANCE;

    public BodyMetricsServiceImpl(BodyMetricsRepository bodyMetricsRepository, AuthUtil authUtil, UserService userService) {
        this.bodyMetricsRepository = bodyMetricsRepository;
        this.authUtil = authUtil;
        this.userService = userService;
    }

    public BodyMetrics createBodyMetrics(BodyMetricsDTO bodyMetricsDTO) {
        Integer userId = authUtil.getAuthenticatedUserId();
        User user = userService.findUserById(userId);

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
    public List<BodyMetrics> getBodyMetricsHistory() {
        Integer currentUserId = authUtil.getAuthenticatedUserId();
        log.info("Get BodyMetrics history by userId: {}", currentUserId);
        return bodyMetricsRepository.findAllByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(currentUserId);
    }

    @Override
    public BodyMetrics getLatestBodyMetrics() {
        Integer currentUserId = authUtil.getAuthenticatedUserId();
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

    // Get all body metrics for the authenticated user
//    public List<BodyMetricsDTO> getBodyMetricsHistory() {
//        Integer userId = authUtil.getAuthenticatedUserId();
//        List<BodyMetrics> bodyMetricsList = bodyMetricsRepository.findByUserIdAndIsDeletedFalseOrderByTimestampDesc(userId);
//
//        return bodyMetricsList.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
}
