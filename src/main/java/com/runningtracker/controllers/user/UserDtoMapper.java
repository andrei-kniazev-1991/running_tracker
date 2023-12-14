package com.runningtracker.controllers.user;

import com.runningtracker.services.user.UserModel;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto userModelToDto(UserModel model);

    UserModel userDtoToModel(UserDto entity);

    List<UserDto> userModelListToDtoList(List<UserModel> users);

    List<UserModel> userDtoListToModelList(List<UserDto> users);

    UserModel userCreateDtoToModel(UserCreateDto entity);
}
