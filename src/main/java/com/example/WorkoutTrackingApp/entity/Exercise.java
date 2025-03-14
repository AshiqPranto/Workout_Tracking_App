package com.example.WorkoutTrackingApp.entity;

import com.example.WorkoutTrackingApp.constants.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exercises")
public class Exercise extends BaseEntity {

    @NotBlank(message = ValidationMessages.NAME_REQUIRED)
    @Size(max = 100, message = ValidationMessages.NAME_MAX_LENGTH)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = ValidationMessages.CATEGORY_REQUIRED)
    @Size(max = 50, message = ValidationMessages.CATEGORY_MAX_LENGTH)
    @Column(nullable = false, length = 50)
    private String category;

    @Size(max = 5000, message = ValidationMessages.INSTRUCTIONS_MAX_LENGTH)
    @Column(length = 500)
    private String instructions;

    @Size(max = 500, message = ValidationMessages.ANIMATION_URL_MAX_LENGTH)
    @Column(name = "animation_url", length = 500)
    private String animationUrl;

    @NotBlank(message = ValidationMessages.BODY_PART_REQUIRED)
    @Size(max = 50, message = ValidationMessages.BODY_PART_MAX_LENGTH)
    @Column(name = "body_part", nullable = false, length = 50)
    private String bodyPart;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ExerciseSets> exerciseSets;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<PersonalRecord> personalRecords = new ArrayList<>();

}
