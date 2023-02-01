package com.javadeveloperzone.service.impl;

import com.javadeveloperzone.domain.Role;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.entity.Users;
import com.javadeveloperzone.repository.UserRepository;
import com.javadeveloperzone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserFormDto userFormDto) {

        // 이메일 중복 확인
        if(userRepository.findByEmail(userFormDto.getEmail()) != null){
            return null;
        }
        // 가입한 성공한 모든 유저는  "USER" 권한 부여
        Users user = userRepository.save(Users.builder()
                .password(passwordEncoder.encode(userFormDto.getPassword()))
                .email(userFormDto.getEmail())
                .role(Role.USER)
                .build());
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
