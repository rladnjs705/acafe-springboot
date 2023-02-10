package com.javadeveloperzone.controller;

import com.javadeveloperzone.config.exception.ResultCodeType;
import com.javadeveloperzone.config.utils.ResponseUtils;
import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.model.ErrorResult;
import com.javadeveloperzone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
	@GetMapping("/user/loginPage")
    @ResponseBody
    public ResponseEntity login(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String exception,
                                Model model) throws Exception {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return ResponseUtils.response(ResultCodeType.ERROR_PARAM, exception);
    }

    @GetMapping("/user/join")
    public String join( Model model ) throws Exception {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/join";
    }

    @GetMapping("/")
    public String login2(@RequestParam Map<String, Object> param, Model model) throws Exception {

        return "redirect:/user/loginPage";
    }
}
