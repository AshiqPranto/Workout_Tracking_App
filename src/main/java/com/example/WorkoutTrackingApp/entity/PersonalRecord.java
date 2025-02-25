package com.example.WorkoutTrackingApp.entity;

import com.example.WorkoutTrackingApp.auth.entity.User;
import com.example.WorkoutTrackingApp.constants.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personal_records")
public class PersonalRecord extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Min(value = 1, message = ValidationMessages.REPS_MIN_Number)
    @Column(nullable = false)
    private Integer reps;

    @Min(value = 0, message = ValidationMessages.WEIGHTS_MIN)
    @Column(nullable = false)
    private Integer weights;

}
