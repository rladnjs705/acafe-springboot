package com.javadeveloperzone.controller.rest;

import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.config.utils.JwtUtil;
import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.model.ResponseVo;
import com.javadeveloperzone.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
        UserDto result = userService.getUser(dto);
        respMap.put("email", result.getEmail());
        respMap.put("role", result.getRole().getValue());
        respMap.put("id", result.getId());

        return ResponseUtils.response(respMap);
    }

}
