package com.example.WorkoutTrackingApp.service.imp;

import com.example.WorkoutTrackingApp.entity.PersonalRecord;
import com.example.WorkoutTrackingApp.exception.ResourceNotFoundException;
import com.example.WorkoutTrackingApp.repository.PersonalRecordRepository;
import com.example.WorkoutTrackingApp.service.PersonalRecordService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalRecordServiceImpl implements PersonalRecordService {

    private final PersonalRecordRepository personalRecordRepository;

    public PersonalRecordServiceImpl(PersonalRecordRepository personalRecordRepository) {
        this.personalRecordRepository = personalRecordRepository;
    }

    @Override
    public Optional<PersonalRecord> getPersonalRecordByExerciseIdandUserId(Integer exerciseId, Integer userId) {
        Optional<PersonalRecord> personalRecord = personalRecordRepository.findByExerciseIdAndUserIdAndIsDeletedFalse(exerciseId, userId);
        return personalRecord;
    }

    @Override
    public PersonalRecord createPersonalRecord(PersonalRecord personalRecord) {
        PersonalRecord createdPersonalRecord = personalRecordRepository.save(personalRecord);
        return createdPersonalRecord;
    }
}
