package com.runningtracker.controllers.runs;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RunsUpdateDto {
    private Long userId;

    private Double finishLatitude;

    private Double finishLongitude;

    private LocalDateTime finishDatetime;

    private Double distance;
}
