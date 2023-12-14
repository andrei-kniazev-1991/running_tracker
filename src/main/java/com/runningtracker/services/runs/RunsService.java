package com.runningtracker.services.runs;

import java.time.LocalDateTime;
import java.util.List;

public interface RunsService {

    RunsModel createRun(RunsModel run);

    RunsModel updateRun(Long runId, RunsModel updatedRun);

    List<RunsModel> getRunsByUserId(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    UserStatisticsModel getUserStatistics(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
