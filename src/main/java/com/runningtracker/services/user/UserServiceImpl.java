package com.runningtracker.services.user;

import com.runningtracker.data.user.UserEntity;
import com.runningtracker.data.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserModelMapper mapper;

    public List<UserModel> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return mapper.userEntityListToModelList(userEntities);
    }

    public Optional<UserModel> getUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return userEntity.map(mapper::userEntityToModel);
    }

    public UserModel addUser(UserModel userModel) {
        UserEntity userEntity = mapper.userModelToEntity(userModel);
        userEntity = userRepository.save(userEntity);
        return mapper.userEntityToModel(userEntity);
    }

    public UserModel updateUser(Long userId, UserModel userModel) {
        Optional<UserEntity> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            UserEntity updatedEntity = mapper.userModelToEntity(userModel);
            updatedEntity.setId(userId);
            updatedEntity = userRepository.save(updatedEntity);
            return mapper.userEntityToModel(updatedEntity);
        } else {
            return null;
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
