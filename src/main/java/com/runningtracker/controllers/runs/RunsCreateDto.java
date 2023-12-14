package com.runningtracker.controllers.runs;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RunsCreateDto {

    @NotEmpty
    private Long userId;

    @NotEmpty
    private Double startLatitude;

    @NotEmpty
    private Double startLongitude;

    @NotNull
    private LocalDateTime startDatetime;
}
