package com.example.WorkoutTrackingApp.service.imp;

import com.example.WorkoutTrackingApp.Mapper.BodyMetricsMapper;
import com.example.WorkoutTrackingApp.auth.service.UserService;
import com.example.WorkoutTrackingApp.dto.BodyMetricsDTO;
import com.example.WorkoutTrackingApp.entity.BodyMetrics;
import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.repository.BodyMetricsRepository;
import com.example.WorkoutTrackingApp.service.BodyMetricsService;
import com.example.WorkoutTrackingApp.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public List<BodyMetricsDTO> getBodyMetricsHistory() {
        return List.of();
    }

    @Override
    public void deleteBodyMetrics(Integer id) {

    }

    // Get all body metrics for the authenticated user
//    public List<BodyMetricsDTO> getBodyMetricsHistory() {
//        Integer userId = authUtil.getAuthenticatedUserId();
//        List<BodyMetrics> bodyMetricsList = bodyMetricsRepository.findByUserIdAndIsDeletedFalseOrderByTimestampDesc(userId);
//
//        return bodyMetricsList.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    // Delete a body metric entry (Soft delete)
//    public void deleteBodyMetrics(Integer id) {
//        BodyMetrics bodyMetrics = bodyMetricsRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Body Metrics not found with ID: " + id));
//
//        bodyMetrics.setDeleted(true);
//        bodyMetricsRepository.save(bodyMetrics);
//    }
}
