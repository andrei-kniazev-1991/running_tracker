package com.runningtracker.controllers.runs;

import com.runningtracker.services.runs.RunsModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RunsDtoMapper {

    @Mapping(target = "id", ignore = true)
    RunsDto runsModelToDto(RunsModel model);

    RunsModel runsDtoToModel(RunsDto dto);

    List<RunsDto> runsModelListToDtoList(List<RunsModel> runs);

    List<RunsModel> runsDtoListToModelList(List<RunsDto> runs);

    @Mapping(target = "id", ignore = true)
    RunsModel runsCreateDtoToModel(RunsCreateDto dto);

    @Mapping(target = "id", ignore = true)
    RunsModel runsUpdateDtoToModel(RunsUpdateDto dto);

    RunsGetDto runsModelToGetDto(RunsModel model);

    List<RunsGetDto> runsModelListToGetDtoList(List<RunsModel> model);
}
