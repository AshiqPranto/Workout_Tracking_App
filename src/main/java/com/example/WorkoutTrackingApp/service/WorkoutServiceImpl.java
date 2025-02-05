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
import org.hibernate.jdbc.Work;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> createWorkout(WorkoutDTO workoutDTO) {
        try {
            Workout workout = convertToEntity(workoutDTO);
            workout = workoutRepository.save(workout);
            return new ResponseEntity<Workout>(workout, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private Workout convertToEntity(WorkoutDTO workoutDTO) {
        String userName = AuthUtil.getAuthenticatedUserName();

        if (userName == null) {
            throw new RuntimeException("User is not authenticated");
        }
        User user = userRepository.findByEmail(userName).get();
        Workout workout = Workout.builder()
                .name(workoutDTO.getName())
                .startTime(LocalDateTime.now())
                .user(user)
                .exerciseSets(List.of())
                .build();
        return workout;
    }

    @Override
    public Workout getWorkoutById(Integer id) {
//        return workoutRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Workout not found with id: " + id));
        return workoutRepository.findByIdAndIsDeletedFalse(id).get();
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAllByIsDeletedFalse();
    }

    @Override
    public List<Workout> getWorkoutsByUserId(Integer userId) {
        return workoutRepository.findByUserIdAndIsDeletedFalse(userId);
    }

    @Override
    public List<Workout> getAllWorkoutsByExerciseId(Integer exerciseId) {
        return workoutRepository.findAllWorkoutsByExerciseId(exerciseId);
    }

    @Override
    public ResponseEntity<?> updateWorkout(Integer id, UpdateWorkoutDTO updateWorkoutDTO) {
        try {
            Workout existingWorkout = getWorkoutById(id);
            existingWorkout.setName(updateWorkoutDTO.getName());
            existingWorkout.setEndTime(updateWorkoutDTO.getEndTime());

            Workout workout = workoutRepository.save(existingWorkout);

            return new ResponseEntity<Workout>(workout, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteWorkout(Integer id) {
        if (!workoutRepository.existsById(id)) {
            throw new EntityNotFoundException("Workout not found with id: " + id);
        }

        Workout workout = workoutRepository.findByIdAndIsDeletedFalse(id).get();
        workout.setDeleted(true);
        workoutRepository.save(workout);
    }
}