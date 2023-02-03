package com.javadeveloperzone.service.impl;

import com.javadeveloperzone.domain.Role;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.entity.Users;
import com.javadeveloperzone.model.ErrorResult;
import com.javadeveloperzone.repository.UserRepository;
import com.javadeveloperzone.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserFormDto userFormDto) {

        // 이메일 중복 확인
//        if(userRepository.findByEmail(userFormDto.getEmail()) != null){
//            return null;
//        }
        //이메일 중복 확인
        validateDuplicateUsers(userFormDto);

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

    private void validateDuplicateUsers(UserFormDto user){
        userRepository.findByEmail(user.getEmail()).ifPresent((m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }));
    }
}