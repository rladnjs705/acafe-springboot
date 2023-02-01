package com.javadeveloperzone.controller;

import com.javadeveloperzone.dto.UserDto;
import com.javadeveloperzone.dto.UserFormDto;
import com.javadeveloperzone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserService userService;
	@GetMapping("/user/loginPage")
    public String login(HttpServletRequest request) throws Exception {

        // 요청 시점의 사용자 URI 정보를 Session의 Attribute에 담아서 전달(잘 지워줘야 함)
        // 로그인이 틀려서 다시 하면 요청 시점의 URI가 로그인 페이지가 되므로 조건문 설정
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/user/loginPage")) {
            request.getSession().setAttribute("prevPage",
                    request.getHeader("Referer"));
        }

        return "user/loginPage";
    }

    @GetMapping("/user/join")
    public String join( Model model ) throws Exception {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/join";
    }

    @PostMapping("/user/signup")
    public String createUser(@Validated UserFormDto userFormDto, BindingResult bindingResult, Model model ) {

        UserDto userDto = userService.createUser(userFormDto);

        if(userDto == null){
            bindingResult.reject("emailDuplicate", "이미 존재하는 아이디입니다.");
        }

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "user/join";
        }

        return "redirect:user/loginPage";
    }

    @GetMapping("/")
    public String login2(@RequestParam Map<String, Object> param, Model model) throws Exception {

        return "redirect:/user/loginPage";
    }
}
