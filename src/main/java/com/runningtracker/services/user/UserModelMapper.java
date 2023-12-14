package com.runningtracker.services.user;

import com.runningtracker.data.user.UserEntity;
import com.runningtracker.services.runs.RunsModelMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = RunsModelMapper.class)
public interface UserModelMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity userModelToEntity(UserModel model);

    UserModel userEntityToModel(UserEntity entity);

    List<UserEntity> userModelListToEntityList(List<UserModel> users);

    List<UserModel> userEntityListToModelList(List<UserEntity> users);
}
