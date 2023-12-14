package com.runningtracker.services.runs;

import lombok.Data;

@Data
public class UserStatisticsModel {

    private Long userId;

    private Integer runCount;

    private Double totalDistance;

    private Double averageSpeed;
}
