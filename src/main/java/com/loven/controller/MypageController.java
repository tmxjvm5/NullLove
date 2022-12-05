package com.loven.controller;

import com.loven.entity.User;
import com.loven.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MypageController {

    @Autowired
    MypageService mypageService;

    @GetMapping("/mypage/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        System.out.println(id);
        mypageService.userDelete(id);
        return "redirect:/logout.do";
    }






}
