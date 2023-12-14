package com.runningtracker.controllers.user;

import com.runningtracker.services.user.UserModel;
import com.runningtracker.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoMapper mapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return mapper.userModelListToDtoList(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        Optional<UserModel> user = userService.getUserById(userId);
        return user.map(value -> new ResponseEntity<>(mapper.userModelToDto(user.get()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserCreateDto userDto) {
        UserModel addedUser = userService.addUser(mapper.userCreateDtoToModel(userDto));
        return new ResponseEntity<>(mapper.userModelToDto(addedUser), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserCreateDto userDto) {
        UserModel updatedUser = userService.updateUser(userId, mapper.userCreateDtoToModel(userDto));
        if (updatedUser != null) {
            return new ResponseEntity<>(mapper.userModelToDto(updatedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
