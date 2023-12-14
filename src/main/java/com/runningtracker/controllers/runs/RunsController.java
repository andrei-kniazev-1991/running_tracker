package com.runningtracker.controllers.runs;

import com.runningtracker.services.runs.RunsModel;
import com.runningtracker.services.runs.RunsService;
import com.runningtracker.services.runs.UserStatisticsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/runs")
public class RunsController {

    @Autowired
    private RunsService runsService;

    @Autowired
    private RunsDtoMapper mapper;

    @Autowired
    private UserStatisticsMapper statisticsMapper;

    @PostMapping
    public ResponseEntity<RunsDto> createRun(@RequestBody RunsCreateDto run) {
        RunsDto createdRun = mapper.runsModelToDto(runsService.createRun(mapper.runsCreateDtoToModel(run)));
        return new ResponseEntity<>(createdRun, HttpStatus.CREATED);
    }

    @PutMapping("/{runId}")
    public ResponseEntity<RunsDto> updateRun(@PathVariable Long runId, @RequestBody RunsUpdateDto updatedRun) {
        RunsDto updatedRunResponse = mapper
                .runsModelToDto(runsService.updateRun(runId, mapper.runsUpdateDtoToModel(updatedRun)));
        return new ResponseEntity<>(updatedRunResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RunsGetDto>> getRunsByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<RunsModel> runs = runsService.getRunsByUserId(userId, startDate, endDate);

        List<RunsGetDto> runsDtoList = mapper.runsModelListToGetDtoList(runs);

        return new ResponseEntity<>(runsDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<UserStatisticsDto> getUserStatistics(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        UserStatisticsModel userStatisticsModel = runsService.getUserStatistics(userId, startDate, endDate);

        UserStatisticsDto userStatisticsDto = statisticsMapper.userStatisticsModelToDto(userStatisticsModel);

        return new ResponseEntity<>(userStatisticsDto, HttpStatus.OK);
    }
}
