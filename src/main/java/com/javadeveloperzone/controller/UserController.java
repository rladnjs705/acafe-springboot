package com.javadeveloperzone.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {

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

    @GetMapping("/")
    public String login2(@RequestParam Map<String, Object> param, Model model) throws Exception {

        return "redirect:/user/loginPage";
    }
}
