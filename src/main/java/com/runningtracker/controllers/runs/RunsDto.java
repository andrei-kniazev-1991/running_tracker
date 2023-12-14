package com.runningtracker.controllers.runs;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RunsDto {

    private Long id;

    private Long userId;

    private Double startLatitude;

    private Double startLongitude;

    private LocalDateTime startDatetime;

    private Double finishLatitude;

    private Double finishLongitude;

    private LocalDateTime finishDatetime;

    private Double distance;
}
