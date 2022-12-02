package com.loven.controller;


import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("signup")
    public String signup() {
        return "signup";
    // 개인 회원가입 페이지
    }
    
    @GetMapping("signup_com")
    public String signup_com(){
        return "signup_com";
    }
    // 기업 회원가입 페이지


	  @GetMapping("/") public String home() {
          return "main"; // 메인페이지 }

      }
    @GetMapping("/empty")
    public String empty() {
        return "empty";
    }
    // 헤더 빈 메인
    @GetMapping("/search")
    public String search() {
        return "search";
    }
    // 추천사이트
    @GetMapping("/most")
    public String most() {
        return "most";
    }
    // 우수사업계획서

    @GetMapping("/write")
    public String write(){
        return "write";
    }
    // 글쓰기 폼

    @GetMapping("/mypage")
    public String mypage(){
        return "mypage";
    }
    // 마이페이지



    }



