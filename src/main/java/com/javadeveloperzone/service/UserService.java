package com.javadeveloperzone.service;

import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;

public interface UserService {
    UserDto createUser(UserFormDto userFormDto);
}
