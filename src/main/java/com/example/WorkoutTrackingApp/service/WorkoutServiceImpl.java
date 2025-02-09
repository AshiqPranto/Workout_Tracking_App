package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.auth.repository.UserRepository;
import com.example.WorkoutTrackingApp.auth.service.JwtService;
import com.example.WorkoutTrackingApp.dto.UpdateWorkoutDTO;
import com.example.WorkoutTrackingApp.dto.WorkoutDTO;
import com.example.WorkoutTrackingApp.entity.Workout;
import com.example.WorkoutTrackingApp.repository.WorkoutRepository;
import com.example.WorkoutTrackingApp.utils.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> createWorkout(WorkoutDTO workoutDTO) {
        log.info("Creating a new workout: {}", workoutDTO.getName());
        try {
            Workout workout = convertToEntity(workoutDTO);
            workout = workoutRepository.save(workout);
            log.info("Workout created successfully with ID: {}", workout.getId());
            return new ResponseEntity<Workout>(workout, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error while creating workout: {}", e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private Workout convertToEntity(WorkoutDTO workoutDTO) {
        String userName = AuthUtil.getAuthenticatedUserName();
        log.debug("Extracted authenticated user: {}", userName);

        if (userName == null) {
            log.error("User is not authenticated");
            throw new RuntimeException("User is not authenticated");
        }
        User user = userRepository.findByEmail(userName).get();
        Workout workout = Workout.builder()
                .name(workoutDTO.getName())
                .startTime(LocalDateTime.now())
                .user(user)
                .exerciseSets(List.of())
                .build();
        log.debug("Converted WorkoutDTO to Workout entity: {}", workout);
        return workout;
    }

    @Override
    public Workout getWorkoutById(Integer id) {
        log.info("Fetching workout by ID: {}", id);
        return workoutRepository.findByIdAndIsDeletedFalse(id).get();
    }

    @Override
    public List<Workout> getAllWorkouts() {
//        return workoutRepository.findAllByIsDeletedFalse();
        log.info("Fetching all workouts.");
        List<Workout> workouts = workoutRepository.findAllByIsDeletedFalse();
        log.debug("Fetched {} workouts", workouts.size());
        return workouts;

    }

    @Override
    public List<Workout> getWorkoutsByUserId(Integer userId) {
        log.info("Fetching workouts for user ID: {}", userId);
        List<Workout> workouts = workoutRepository.findByUserIdAndIsDeletedFalse(userId);
        log.debug("Found {} workouts for user ID: {}", workouts.size(), userId);
        return workouts;
    }

    @Override
    public List<Workout> getAllWorkoutsByExerciseId(Integer exerciseId) {
        log.info("Fetching workouts by exercise ID: {}", exerciseId);
        return workoutRepository.findAllWorkoutsByExerciseId(exerciseId);
    }

    @Override
    public ResponseEntity<?> updateWorkout(Integer id, UpdateWorkoutDTO updateWorkoutDTO) {
        log.info("Updating workout ID: {}", id);
        try {
            Workout existingWorkout = getWorkoutById(id);
            existingWorkout.setName(updateWorkoutDTO.getName());
            existingWorkout.setEndTime(updateWorkoutDTO.getEndTime());

            Workout updatedWorkout = workoutRepository.save(existingWorkout);
            log.info("Workout updated successfully: ID: {}, Name: {}", updatedWorkout.getId(), updatedWorkout.getName());

            return new ResponseEntity<Workout>(updatedWorkout, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error updating workout ID: {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteWorkout(Integer id) {
        log.info("Deleting workout ID: {}", id);

        if (!workoutRepository.existsById(id)) {
            log.error("Workout not found with ID: {}", id);
            throw new EntityNotFoundException("Workout not found with id: " + id);
        }

        Workout workout = workoutRepository.findByIdAndIsDeletedFalse(id).get();
        workout.setDeleted(true);
        workoutRepository.save(workout);
        log.info("Workout successfully soft-deleted with ID: {}", id);
    }
}