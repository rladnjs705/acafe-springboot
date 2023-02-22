package com.javadeveloperzone.service;

import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.entity.Users;

import java.util.List;

public interface UserService {
    UserDto createUser(UserFormDto userFormDto);
    Users getUser(UserDto userDto);
    Users getUserId(UserDto userDto);
    List<Users> getUserList();
}
