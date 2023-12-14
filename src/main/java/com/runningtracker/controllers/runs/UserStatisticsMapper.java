package com.runningtracker.controllers.runs;

import com.runningtracker.services.runs.UserStatisticsModel;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserStatisticsMapper {

    UserStatisticsDto userStatisticsModelToDto(UserStatisticsModel model);

    UserStatisticsModel userStatisticsDtoToModel(UserStatisticsDto dto);

    List<UserStatisticsDto> userStatisticsModelListToDtoList(List<UserStatisticsModel> userStatistics);

    List<UserStatisticsModel> userStatisticsDtoListToModelList(List<UserStatisticsDto> userStatistics);
}
