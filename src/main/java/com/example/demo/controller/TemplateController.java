package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
    @GetMapping("/courses")
    public String getCourses(){
        return "courses";
    }
}
