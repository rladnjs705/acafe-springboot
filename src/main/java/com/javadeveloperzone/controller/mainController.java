package com.javadeveloperzone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class mainController {

	@GetMapping("/index")
    public String index(@RequestParam Map<String, Object> param, Model model) throws Exception {

        return "index";
    }

}
