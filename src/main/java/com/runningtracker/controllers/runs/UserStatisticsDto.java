package com.runningtracker.controllers.runs;

import lombok.Data;

@Data
public class UserStatisticsDto {

    private Long userId;

    private Integer runCount;

    private Double totalDistance;

    private Double averageSpeed;
}
