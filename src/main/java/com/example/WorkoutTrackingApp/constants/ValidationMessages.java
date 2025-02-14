package com.example.WorkoutTrackingApp.constants;

import org.springframework.security.web.webauthn.api.PublicKeyCose;

public class ValidationMessages {

    public static final String NAME_REQUIRED = "Name is required";
    public static final String NAME_MAX_LENGTH = "Name must not exceed 100 characters";

    public static final String CATEGORY_REQUIRED = "Category is required";
    public static final String CATEGORY_MAX_LENGTH = "Category must not exceed 50 characters";

    public static final String INSTRUCTIONS_MAX_LENGTH = "Instructions must not exceed 5000 characters";

    public static final String ANIMATION_URL_MAX_LENGTH = "Animation URL must not exceed 500 characters";

    public static final String BODY_PART_REQUIRED = "Body part is required";
    public static final String BODY_PART_MAX_LENGTH = "Body part must not exceed 50 characters";

    public static final String REPS_MIN_Number = "Reps must be at least 1";
    public static final String WEIGHTS_MIN = "Weight must be at least 0";

    public static final String WORKOUT_NAME_REQUIRED = "Workout name is required";
}
