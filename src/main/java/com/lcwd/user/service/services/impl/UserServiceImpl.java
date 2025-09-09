package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.payload.UserDto;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        User user = modelMapper.map(userDto, User.class);

        user.setUserId(randomUserId);
        User newUser = userRepository.save(user);
        return modelMapper.map(newUser,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> usersList = userRepository.findAll();
        return usersList.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server :  " + userId));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server :  " + userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser,UserDto.class);
    }
}
