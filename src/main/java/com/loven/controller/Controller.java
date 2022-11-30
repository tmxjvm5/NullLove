package com.loven.controller;


import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("join")
    public String join() {
        return "join";
    }

    @GetMapping("/")
        public String home() {
            return "main";
        }
    }

