package com.javadeveloperzone.controller.rest;

import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.config.utils.JwtUtil;
import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.dto.CategoryDto;
import com.javadeveloperzone.dto.ItemDto;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.entity.Category;
import com.javadeveloperzone.entity.Users;
import com.javadeveloperzone.model.ResponseVo;
import com.javadeveloperzone.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    @PostMapping("/user/create")
    public ResponseEntity<ResponseVo> createUser(@Validated UserFormDto userFormDto, BindingResult bindingResult, Model model ) {

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        userService.createUser(userFormDto);

        return ResponseUtils.response(ResultCodeType.SUCCESS, null);
    }
    @GetMapping("/user/login/info")
    public ResponseEntity<ResponseVo> getUser(@Validated UserDto userDto, BindingResult bindingResult, Model model ) {

        HashMap<String,Object> respMap = new HashMap<String,Object>();

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        String email = JwtUtil.getUserIdFromJWT(userDto.getAuthToken());
        UserDto dto = UserDto.builder()
                .email(email)
                .build();
        Users user = userService.getUser(dto);
        UserDto result = UserDto.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .id(user.getId())
                .build();
        respMap.put("user", result);

        return ResponseUtils.response(respMap);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<ResponseVo> getUsers() {
        Map<String,Object> respMap = new HashMap<String, Object>();

        List<Users> userList = userService.getUserList();
        List<UserDto> result = userList.stream().map(
                m -> new UserDto(m.getId(),m.getEmail(),m.getRole())
        ).collect(Collectors.toList());

        respMap.put("list", result);

        return ResponseUtils.response(respMap);
    }

    @PutMapping("/admin/userAuth/update")
    @Transactional
    public ResponseEntity<ResponseVo> updateUserAuth(@Validated UserDto userDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return ResponseUtils.response(ResultCodeType.ERROR_PARAM, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        UserDto dto = new UserDto();
        dto.setId(userDto.getId());

        Users user = userService.getUserId(dto);
        user.updateRole(userDto.getRole());

        UserDto result = UserDto.builder().id(user.getId()).role(user.getRole()).email(user.getEmail()).build();

        HashMap<String,Object> respMap = new HashMap<>();
        respMap.put("user", result);

        return ResponseUtils.response(respMap);
    }

}
