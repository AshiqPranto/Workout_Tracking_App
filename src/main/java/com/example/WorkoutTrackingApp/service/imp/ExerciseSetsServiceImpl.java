package com.example.WorkoutTrackingApp.service.imp;

import com.example.WorkoutTrackingApp.Mapper.ExerciseSetsMapper;
import com.example.WorkoutTrackingApp.dto.ExerciseSetDTO;
import com.example.WorkoutTrackingApp.entity.Exercise;
import com.example.WorkoutTrackingApp.entity.ExerciseSets;
import com.example.WorkoutTrackingApp.entity.PersonalRecord;
import com.example.WorkoutTrackingApp.entity.Workout;
import com.example.WorkoutTrackingApp.exception.ResourceNotFoundException;
import com.example.WorkoutTrackingApp.repository.ExerciseRepository;
import com.example.WorkoutTrackingApp.repository.ExerciseSetsRepository;
import com.example.WorkoutTrackingApp.repository.PersonalRecordRepository;
import com.example.WorkoutTrackingApp.repository.WorkoutRepository;
import com.example.WorkoutTrackingApp.service.ExerciseSetsService;
import com.example.WorkoutTrackingApp.service.PersonalRecordService;
import com.example.WorkoutTrackingApp.utils.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseSetsServiceImpl implements ExerciseSetsService {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseSetsServiceImpl.class);

    private final ExerciseSetsRepository exerciseSetsRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final AuthUtil authUtil;
    private final PersonalRecordService personalRecordService;
    private final PersonalRecordRepository personalRecordRepository;

    @Override
    public ExerciseSets createExerciseSet(ExerciseSetDTO exerciseSetDTO) {
        logger.info("Creating ExerciseSet with Exercise ID: {} and Workout ID: {}",
                exerciseSetDTO.getExerciseId(), exerciseSetDTO.getWorkoutId());

        ExerciseSetsMapper exerciseSetsMapper = ExerciseSetsMapper.INSTANCE;
        logger.debug("Converting ExerciseSetDTO to ExerciseSets entity");
        ExerciseSets exerciseSets = exerciseSetsMapper.ExerciseSetDTOToExerciseSets(exerciseSetDTO);

        Exercise exercise = exerciseRepository.findByIdAndIsDeletedFalse(exerciseSetDTO.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + exerciseSetDTO.getExerciseId()));
        Workout workout = workoutRepository.findByIdAndIsDeletedFalse(exerciseSetDTO.getWorkoutId())
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + exerciseSetDTO.getWorkoutId()));

        exerciseSets.setExercise(exercise);
        exerciseSets.setWorkout(workout);

        ExerciseSets savedExerciseSets = exerciseSetsRepository.save(exerciseSets);

        logger.debug("ExerciseSet created successfully with ID: {}", savedExerciseSets.getId());

        checkForNewPersonalRecord(savedExerciseSets);

        return savedExerciseSets;
    }

    private void checkForNewPersonalRecord(ExerciseSets exerciseSets) {
        log.info("Checking for new personal records for ExerciseSets Id: {}", exerciseSets.getId());

        Integer currentUserId = authUtil.getAuthenticatedUserId();
        Integer exerciseId = exerciseSets.getExercise().getId();
        Optional<PersonalRecord> existingPersonalRecordOpt = personalRecordService.getPersonalRecordByExerciseIdandUserId(
                exerciseId, currentUserId);

        if(existingPersonalRecordOpt.isEmpty()) {
            createNewPersonalRecord(currentUserId, exerciseSets);
        } else {
            updatePersonalRecordIfBetter(existingPersonalRecordOpt.get(), exerciseSets);
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
        boolean updated = isThisNewMaxWeights(personalRecord, exerciseSet) | isThisNewMaxReps(personalRecord, exerciseSet);

        if (updated) {
            personalRecordRepository.save(personalRecord);
            log.info("Personal record updated for exercise ID: {}", exerciseSet.getExercise().getId());
        }
    }

    private static boolean isThisNewMaxWeights(PersonalRecord personalRecord, ExerciseSets exerciseSet) {
        if (exerciseSet.getWeights() > personalRecord.getWeights()) {
            personalRecord.setWeights(exerciseSet.getWeights());
            log.info("Updated weights personal record for exercise ID: {} to {}", exerciseSet.getExercise().getId(), exerciseSet.getWeights());
            return true;
        }
        return false;
    }

    private static boolean isThisNewMaxReps(PersonalRecord personalRecord, ExerciseSets exerciseSet) {
        if (exerciseSet.getReps() > personalRecord.getReps()) {
            personalRecord.setReps(exerciseSet.getReps());
            log.info("Updated reps personal record for exercise ID: {} to {}", exerciseSet.getExercise().getId(), exerciseSet.getReps());
            return true;
        }
        return false;
    }

    @Override
    public ExerciseSets getExerciseSetById(Integer id) {
        logger.info("Fetching ExerciseSet with ID: {}", id);

        ExerciseSets exerciseSets = exerciseSetsRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExerciseSet not found with ID: " + id));

        return exerciseSets;
    }

    @Override
    public List<ExerciseSets> getAllExerciseSets() {
        logger.info("Fetching all ExerciseSets that are not deleted");

        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findAllByIsDeletedFalse();
        logger.debug("Found {} ExerciseSets", exerciseSetsList.size());

        return exerciseSetsList;
    }

    @Override
    public List<ExerciseSets> getExerciseSetsByExerciseId(Integer exerciseId) {
        logger.info("Fetching ExerciseSets for Exercise ID: {}", exerciseId);
        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findByExerciseIdAndIsDeletedFalse(exerciseId);
        logger.debug("Found {} ExerciseSets for Exercise ID: {}", exerciseSetsList.size(), exerciseId);
        return exerciseSetsList;
    }

    @Override
    public List<ExerciseSets> getExerciseSetsByWorkoutId(Integer workoutId) {
        logger.info("Fetching ExerciseSets for Workout ID: {}", workoutId);
        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findByWorkoutIdAndIsDeletedFalse(workoutId);
        logger.debug("Found {} ExerciseSets for Workout ID: {}", exerciseSetsList.size(), workoutId);
        return exerciseSetsList;
    }

    @Override
    public List<ExerciseSets> getExerciseSetsByUserId(Integer userId) {
        logger.info("Fetching ExerciseSets for User ID: {}", userId);
        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findAllByUserId(userId);
        logger.debug("Found {} ExerciseSets for User ID: {}", exerciseSetsList.size(), userId);
        return exerciseSetsList;
    }

    @Override
    public List<ExerciseSets> getAllByExerciseIdAndUserId(Integer exerciseId, Integer userId) {
        logger.info("Fetching ExerciseSets for Exercise ID: {} and User ID: {}", exerciseId, userId);
        List<ExerciseSets> exerciseSetsList = exerciseSetsRepository.findAllByExerciseIdAndUserId(exerciseId, userId);
        logger.debug("Found {} ExerciseSets for Exercise ID: {} and User ID: {}", exerciseSetsList.size(), exerciseId, userId);
        return exerciseSetsList;
    }

    @Override
    public ExerciseSets updateExerciseSet(Integer id, ExerciseSetDTO exerciseSetDTO) {
        logger.info("Updating ExerciseSet with ID: {}", id);
        ExerciseSets existingExerciseSet = getExerciseSetById(id);
        existingExerciseSet = setPropertyToExistingExercise(existingExerciseSet, exerciseSetDTO);

        ExerciseSets updatedExerciseSet = exerciseSetsRepository.save(existingExerciseSet);
        logger.info("ExerciseSet updated successfully with ID: {}", updatedExerciseSet.getId());

        return updatedExerciseSet;
    }

    private ExerciseSets setPropertyToExistingExercise(ExerciseSets existingExerciseSet, ExerciseSetDTO exerciseSetDTO) {
        existingExerciseSet.setReps(exerciseSetDTO.getReps());
        existingExerciseSet.setWeights(exerciseSetDTO.getWeights());
        existingExerciseSet.setExercise(exerciseRepository.findById(exerciseSetDTO.getExerciseId()).get());
        existingExerciseSet.setWorkout(workoutRepository.findById(exerciseSetDTO.getWorkoutId()).get());
        return existingExerciseSet;
    }

    @Override
    public void deleteExerciseSet(Integer id) {
        logger.info("Soft deleting ExerciseSet with ID: {}", id);
        if (!exerciseSetsRepository.existsById(id)) {
            logger.warn("ExerciseSet with ID {} not found for deletion", id);
            throw new EntityNotFoundException("ExerciseSet not found with id: " + id);
        }
        ExerciseSets exerciseSets = getExerciseSetById(id);
        exerciseSets.setDeleted(true);

        exerciseSetsRepository.save(exerciseSets);
        logger.info("ExerciseSet soft deleted successfully with ID: {}", id);
    }
}