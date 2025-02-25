package com.example.WorkoutTrackingApp.service.imp;

import com.example.WorkoutTrackingApp.Mapper.WorkoutMapper;
import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.auth.repository.UserRepository;
import com.example.WorkoutTrackingApp.auth.service.JwtService;
import com.example.WorkoutTrackingApp.dto.UpdateWorkoutDTO;
import com.example.WorkoutTrackingApp.dto.WorkoutDTO;
import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import com.example.WorkoutTrackingApp.entity.PersonalRecord;
import com.example.WorkoutTrackingApp.entity.Workout;
import com.example.WorkoutTrackingApp.exception.ResourceNotFoundException;
import com.example.WorkoutTrackingApp.repository.PersonalRecordRepository;
import com.example.WorkoutTrackingApp.repository.WorkoutRepository;
import com.example.WorkoutTrackingApp.service.ExerciseService;
import com.example.WorkoutTrackingApp.service.ExerciseSetsService;
import com.example.WorkoutTrackingApp.service.PersonalRecordService;
import com.example.WorkoutTrackingApp.service.WorkoutService;
import com.example.WorkoutTrackingApp.utils.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ExerciseSetsService exerciseSetsService;
    private final PersonalRecordService personalRecordService;
    private final AuthUtil authUtil;
    private final ExerciseService exerciseService;
    private final PersonalRecordRepository personalRecordRepository;

    @Override
    public Workout createWorkout(WorkoutDTO workoutDTO) {
        log.info("Creating a new workout: {}", workoutDTO.getName());
        WorkoutMapper workoutMapper = WorkoutMapper.INSTANCE;
        Workout workout = workoutMapper.workoutDTOToWorkout(workoutDTO);

        String userName = AuthUtil.getAuthenticatedUserName();
        log.debug("Extracted authenticated user: {}", userName);

        if (userName == null) {
            log.error("User is not authenticated");
            throw new RuntimeException("User is not authenticated");
        }
        User user = userRepository.findByEmailAndIsDeletedFalse(userName)
                        .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userName));

        workout.setUser(user);
        workout = workoutRepository.save(workout);
        log.info("Workout created successfully with ID: {}", workout.getId());

        return workout;
    }

    @Override
    public Workout getWorkoutById(Integer id) {
        log.info("Fetching workout by ID: {}", id);
        return workoutRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with ID: " + id));
    }

    @Override
    public List<Workout> getAllWorkouts() {
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
    public Workout updateWorkout(Integer id, UpdateWorkoutDTO updateWorkoutDTO) {
        log.info("Updating workout ID: {}", id);
        Workout existingWorkout = getWorkoutById(id);

        existingWorkout.setName(updateWorkoutDTO.getName());
        existingWorkout.setEndTime(updateWorkoutDTO.getEndTime());

        Workout updatedWorkout = workoutRepository.save(existingWorkout);
        log.info("Workout updated successfully: ID: {}, Name: {}", updatedWorkout.getId(), updatedWorkout.getName());

        return updatedWorkout;
    }

    @Override
    public void deleteWorkout(Integer id) {
        log.info("Deleting workout ID: {}", id);

        Workout workout = workoutRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with ID: " + id));

        workout.setDeleted(true);
        workoutRepository.save(workout);
        log.info("Workout successfully soft-deleted with ID: {}", id);
    }

    @Override
    public void endWorkout(Integer id) {
        log.info("Ending workout with ID: {}", id);

        Workout workout = getWorkoutById(id);
        workout.setEndTime(LocalDateTime.now());

        workoutRepository.save(workout);
        log.info("Workout successfully ended with ID: {}", id);

        checkPersonalRecords(id);
    }

    public void checkPersonalRecords(Integer workoutId) {
        log.info("Checking for new personal records from workout ID: {}", workoutId);

        List<ExerciseSets> exerciseSets = exerciseSetsService.getExerciseSetsByWorkoutId(workoutId);

        if (exerciseSets.isEmpty()) {
            log.info("No exercise sets found for workout ID: {}", workoutId);
            return;
        }
        Integer currentUserId = authUtil.getAuthenticatedUserId();

        for(ExerciseSets exerciseSet : exerciseSets) {
            Integer exerciseId = exerciseSet.getExercise().getId();
            Optional<PersonalRecord> existingPersonalRecordOpt = personalRecordService.getPersonalRecordByExerciseIdandUserId(
                    exerciseId, currentUserId);

            if(existingPersonalRecordOpt.isEmpty()) {
                createNewPersonalRecord(currentUserId, exerciseSet);
            } else {
                updatePersonalRecordIfBetter(existingPersonalRecordOpt.get(), exerciseSet);
            }
        }
    }
    private void createNewPersonalRecord(Integer userId, ExerciseSets exerciseSet) {
        log.info("Creating new personal record for user ID: {} and exercise ID: {}", userId, exerciseSet.getExercise().getId());

        PersonalRecord newRecord = new PersonalRecord().builder()
                .reps(exerciseSet.getReps())
                .weights(exerciseSet.getWeights())
                .exercise(exerciseSet.getExercise())
                .user(exerciseSet.getWorkout().getUser())
                .build();

        personalRecordService.createPersonalRecord(newRecord);
        log.info("New personal record created for exercise ID: {}", exerciseSet.getExercise().getId());
    }

    private void updatePersonalRecordIfBetter(PersonalRecord personalRecord, ExerciseSets exerciseSet) {
        boolean updated = false;

        if (exerciseSet.getReps() > personalRecord.getReps()) {
            personalRecord.setReps(exerciseSet.getReps());
            updated = true;
            log.info("Updated reps personal record for exercise ID: {} to {}", exerciseSet.getExercise().getId(), exerciseSet.getReps());
        }

        if (exerciseSet.getWeights() > personalRecord.getWeights()) {
            personalRecord.setWeights(exerciseSet.getWeights());
            updated = true;
            log.info("Updated weights personal record for exercise ID: {} to {}", exerciseSet.getExercise().getId(), exerciseSet.getWeights());
        }

        if (updated) {
            personalRecordRepository.save(personalRecord);
            log.info("Personal record updated for exercise ID: {}", exerciseSet.getExercise().getId());
        }
    }

}