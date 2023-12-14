package com.runningtracker.services.runs;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RunsModel {

    private Long id;

    private Long userId;

    private Double startLatitude;

    private Double startLongitude;

    private LocalDateTime startDatetime;

    private Double finishLatitude;

    private Double finishLongitude;

    private LocalDateTime finishDatetime;

    private Double distance;

    private Double averageSpeed;
}
