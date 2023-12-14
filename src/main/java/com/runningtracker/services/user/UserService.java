package com.runningtracker.services.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserModel> getAllUsers();

    Optional<UserModel> getUserById(Long userId);

    UserModel addUser(UserModel userModel);

    UserModel updateUser(Long userId, UserModel userModel);

    void deleteUser(Long userId);
}
