package com.example.WorkoutTrackingApp.constants;

import java.security.PublicKey;

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

    public static final String WEIGHT_NOT_NULL = "Weight cannot be null";
    public  static final String WEIGHT_POSITIVE = "Weight must be a positive number";

    public static final String HEIGHT_NOT_NULL = "Height cannot be null";
    public static final String HEIGHT_POSITIVE = "Height must be a positive number";

    public static final String BODY_FAT_PERCENTAGE_MIN = "Body fat percentage must be at least 0%";
    public static final String BODY_FAT_PERCENTAGE_MAX = "Body fat percentage cannot exceed 100%";

    public static final String MUSCLE_MASS_ZERO_OR_POSITIVE = "Muscle mass must be zero or a positive number";

    public static final String BMI_ZERO_OR_POSITIVE = "Bmi must be zero or a positive number";

    public static final String HIP_CIRCUMFERENCE_ZERO_OR_POSITIVE = "Hip circumference must be zero or a positive number";

    public static final String CHEST_MEASUREMENT_ZERO_OR_POSITIVE = "chest measurement must be zero or a positive number";
}
