package com.example.WorkoutTrackingApp.service;

import com.example.WorkoutTrackingApp.entity.PersonalRecord;

import java.util.Optional;

public interface PersonalRecordService {

    Optional<PersonalRecord> getPersonalRecordByExerciseIdandUserId(Integer workoutId, Integer userId);

    PersonalRecord createPersonalRecord(PersonalRecord personalRecord);
}
