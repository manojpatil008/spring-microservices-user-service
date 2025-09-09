package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.payload.UserDto;

import java.util.List;

public interface UserService {
    //create
    UserDto saveUser(UserDto userDto);

    //get all user
    List<UserDto> getAllUser();

    //get single user
    UserDto getUser(String userId);

    //delete the user
    void deleteUser(String userId);

    //update the user
    UserDto updateUser(UserDto userDto, String userId);

}
