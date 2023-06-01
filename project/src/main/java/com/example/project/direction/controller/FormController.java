package com.example.project.direction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FormController {


    @GetMapping("/")
    public String main() {
        return "main"; // 접두어(prefix), 접미어(suffix) 자동 매핑됨
    }








}
