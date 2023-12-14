package com.runningtracker.data.runs;

import com.runningtracker.data.user.UserEntity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class RunsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private Double startLatitude;

    private Double startLongitude;

    private LocalDateTime startDatetime;

    private Double finishLatitude;

    private Double finishLongitude;

    private LocalDateTime finishDatetime;

    private Double distance;
}
