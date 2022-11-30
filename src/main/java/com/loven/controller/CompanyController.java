package com.loven.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.loven.entity.Company;
import com.loven.entity.User;
import com.loven.service.BoardService;

@Controller
public class CompanyController {
	@Autowired
	BoardService service;
	//회원가입
	@GetMapping("/cJoin.do")
	public String cJoinForm() {
		
		return "cJoin";
	}
		
	@PostMapping("/cJoin.do")
		public String cJoin(HttpSession session, Company vo,User uvo) {
			System.out.println(vo);
			service.joinInsert(uvo);
			service.cInsert(vo);
			System.out.println("입력완료");
			session.setAttribute("vo", vo);
			

			return "redirect:/login.do";
		}
	
}
