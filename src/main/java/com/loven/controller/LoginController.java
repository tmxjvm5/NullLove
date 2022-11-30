package com.loven.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loven.entity.Company;
import com.loven.entity.User;
import com.loven.service.BoardService;

@Controller
public class LoginController {
	@Autowired
	BoardService service;

// 회원가입 폼	
	@GetMapping("/join.do")
	public String joinForm() {

		return "join";
	}

//회원가입
	@PostMapping("/join.do")
	public String join(HttpSession session, User vo) {
		service.joinInsert(vo);
		session.setAttribute("vo", vo);
		User mvo = service.loginCheck(vo);
		if (mvo != null) {
			session.setAttribute("mvo", mvo);
		}

		return "redirect:/login.do";
	}

//아이디 중복체크
	@PostMapping("/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {

		int cnt = service.idCheck(id);
		return cnt;
	}

	// 로그인
	@PostMapping("/login.do")
	public String loginCheck(String check,User vo, HttpSession session) {
		
		User mvo = service.loginCheck(vo);
		if (mvo != null) {
			if(check.equals("기업")) {
			Company cvo =service.companyCheck(vo);
			
				session.setAttribute("cvo", cvo);
			}
			session.setAttribute("mvo", mvo);
			System.out.println(mvo);
			return "redirect:/main";
		}
		return "login";
	}
	@RequestMapping("/logout.do")
	public String loginOut(HttpSession session) {
		session.invalidate(); //무효화
		return "redirect:/login.do";
	}

	// 로그인 폼
	@GetMapping("/login.do")
	public String loginForm() {

		return "login";
	}

	@RequestMapping("main")
	public String main() {
		
		return "main";
	}
	// 마이페이지 이동
		@GetMapping("/myPage.do")
		public String myPage(){
		
			return "myPage";
		}
		@PostMapping
		public String memberUpdate(User vo, HttpSession session){
			
			service.memberUpdate(vo);
			
			session.invalidate();
			
			return "redirect:main";
	}
	
}
