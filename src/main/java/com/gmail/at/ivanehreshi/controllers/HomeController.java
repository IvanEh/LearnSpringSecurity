package com.gmail.at.ivanehreshi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Hello, World";
    }
}
