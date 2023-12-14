package com.runningtracker.services.runs;

import com.runningtracker.data.runs.RunsEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RunsModelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    RunsEntity runsModelToEntity(RunsModel model);

    @Mapping(target = "userId", source = "user.id")
    RunsModel runsEntityToModel(RunsEntity entity);

    List<RunsEntity> runsModelListToEntityList(List<RunsModel> runs);

    List<RunsModel> runsEntityListToModelList(List<RunsEntity> runs);
}
