package com.runningtracker.services.runs;

import com.runningtracker.data.runs.RunsEntity;
import com.runningtracker.data.runs.RunsRepository;
import com.runningtracker.data.user.UserEntity;
import com.runningtracker.data.user.UserRepository;
import com.runningtracker.exceptions.IncorrectRunDataException;
import com.runningtracker.utils.MathUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RunsServiceImpl implements RunsService {

    @Autowired
    private RunsRepository runsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RunsModelMapper mapper;

    public RunsModel createRun(RunsModel run) {
        if (run.getStartLatitude() == null || run.getStartLongitude() == null || run.getStartDatetime() == null) {
            throw new IncorrectRunDataException("Incorrect data input for run");
        }

        UserEntity user = userRepository.getById(run.getUserId());
        RunsEntity runsEntity = mapper.runsModelToEntity(run);
        runsEntity.setUser(user);
        return mapper.runsEntityToModel(runsRepository.save(runsEntity));
    }

    public RunsModel updateRun(Long runId, RunsModel updatedRun) {
        if (updatedRun.getFinishLatitude() == null || updatedRun.getFinishLongitude() == null
                || updatedRun.getFinishDatetime() == null || updatedRun.getFinishDatetime()
                .isBefore(updatedRun.getStartDatetime())) {
            throw new IncorrectRunDataException("Incorrect data input for run");
        }

        RunsEntity existingRun = runsRepository.findById(runId)
                .orElseThrow(() -> new RuntimeException("Run not found with id: " + runId));

        existingRun.setFinishLatitude(updatedRun.getFinishLatitude());
        existingRun.setFinishLongitude(updatedRun.getFinishLongitude());
        existingRun.setFinishDatetime(updatedRun.getFinishDatetime());
        if (updatedRun.getDistance() != null && updatedRun.getDistance() != 0) {
            existingRun.setDistance(updatedRun.getDistance());
        } else {
            existingRun.setDistance(MathUtils.calculateDistanceBetweenPointsWithHypot(existingRun.getStartLatitude(),
                    existingRun.getStartLongitude(), existingRun.getFinishLatitude(),
                    existingRun.getFinishLongitude()));
        }

        return mapper.runsEntityToModel(runsRepository.save(existingRun));
    }

    public List<RunsModel> getRunsByUserId(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<RunsModel> runs;

        if (startDate != null && endDate != null) {
            runs = mapper.runsEntityListToModelList(runsRepository
                    .findByUserIdAndStartDatetimeBetweenAndFinishDatetimeBetween(userId, startDate, endDate, startDate,
                            endDate));
        } else {
            runs = mapper.runsEntityListToModelList(runsRepository.findByUserId(userId));
        }

        for (RunsModel runsModel : runs) {
            if (runsModel.getStartDatetime() != null && runsModel.getFinishDatetime() != null
                    && runsModel.getDistance() != null && runsModel.getDistance() > 0) {
                runsModel.setAverageSpeed(MathUtils
                        .calculateAverageSpeed(runsModel.getStartDatetime(), runsModel.getFinishDatetime(),
                                runsModel.getDistance()));
            }
        }

        return runs;
    }

    public UserStatisticsModel getUserStatistics(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<RunsModel> runs;

        if (startDate != null && endDate != null) {
            runs = mapper.runsEntityListToModelList(runsRepository
                    .findByUserIdAndStartDatetimeBetweenAndFinishDatetimeBetween(userId, startDate, endDate, startDate,
                            endDate));
        } else {
            runs = mapper.runsEntityListToModelList(runsRepository.findByUserId(userId));
        }

        double totalDistance = runs.stream().mapToDouble(RunsModel::getDistance).sum();
        double averageSpeed = runs.stream().mapToDouble(runsModel -> MathUtils
                .calculateAverageSpeed(runsModel.getStartDatetime(), runsModel.getFinishDatetime(),
                        runsModel.getDistance())).average().orElse(0.0);

        UserStatisticsModel userStatisticsModel = new UserStatisticsModel();
        userStatisticsModel.setUserId(userId);
        userStatisticsModel.setRunCount(runs.size());
        userStatisticsModel.setTotalDistance(totalDistance);
        userStatisticsModel.setAverageSpeed(averageSpeed);

        return userStatisticsModel;
    }
}
