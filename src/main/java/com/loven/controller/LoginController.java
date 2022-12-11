package com.loven.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.loven.service.BoardServiceImpl;
import com.loven.service.MemberService;
import com.loven.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.loven.entity.Company;
import com.loven.entity.Script;
import com.loven.entity.User;
import com.loven.service.BoardService;

import java.util.HashMap;

@Controller
public class LoginController {
	@Autowired
	BoardService service;

	@Autowired
	OAuthService oAuthService;

	@Autowired
	BoardServiceImpl boardService;

	@Autowired
	MemberService memberService;

	/*
	 * // 회원가입 폼
	 * 
	 * @GetMapping("/signup") public String joinForm() {
	 * 
	 * return "signup"; }
	 */

//회원가입
	@PostMapping("/signup")
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
	public String loginCheck(String check,User vo, HttpSession session,RedirectAttributes rttr) {
		
		User mvo = service.loginCheck(vo);
		if (mvo != null) {
			if(vo.getLogin_type().equals("C")) {
			
				Company cvo =service.companyCheck(vo);

				session.setAttribute("mvo",mvo);

				session.setAttribute("cvo", cvo);
				
				
			}else if(vo.getLogin_type().equals("a")) { // 관리자인 경우
				mvo = service.loginAdmin(vo); // 관리자용 로그인
				session.setAttribute("mvo", mvo);
				System.out.println(mvo);
			}else {

			//session.setAttribute("mvo", mvo);

			rttr.addFlashAttribute("msg", false);
		}
			return "redirect:/main";
		}else {
			
		return "login";
			}
		
		}
	@RequestMapping("/logout.do")
	public String loginOut(HttpSession session) {
		session.invalidate(); //무효화
		return "redirect:/main";
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
		/*@GetMapping("/myPage.do")
		public String myPage() {

			return "myPage";
		}*/
		@PostMapping
		public String memberUpdate(User vo, HttpSession session){
			
			service.memberUpdate(vo);
			
			session.invalidate();
			
			return "redirect:main";
	}

	
	  @GetMapping("login") public String login(Model model) {
	  model.addAttribute("data", "hello"); return "login"; }
	

	//---------------------------------여기서부터 카카오 login Api--------------------------------------------

	@RequestMapping(value = "/klogin")
	public String login(@RequestParam(value = "code", required = false) String code, HttpSession session, HttpServletResponse response) throws Exception{

		System.out.println("loginController!!");

		System.out.println("####code#####" + code);

		String access_Token = oAuthService.getAccessToken(code);
		System.out.println("###access_Token#### : " + access_Token);

		Cookie token = new Cookie("authorize-access-token", access_Token);
		token.setPath("/");
		response.addCookie(token);

		//*
		HashMap<String, String> userInfo = oAuthService.getUserInfo(access_Token);
		System.out.println("###user_info### : " + userInfo);

		String mem_id = userInfo.get("id");
		String mem_pw = userInfo.get("id");
		String mem_name = userInfo.get("nickname");
		String mem_email = userInfo.get("email");

		int check = boardService.idCheck(mem_id);

		if ( check != 1 ){
			System.out.println(mem_id);
			System.out.println(mem_name);
			System.out.println(mem_email);

			User vo = new User();
			vo.setId(mem_id);
			vo.setPw(mem_id);
			vo.setName(mem_name);
			vo.setEmail(mem_email);
			memberService.kakaoLogin(vo);

			return "redirect:/";
		} else {

			User vo = new User();
			vo.setId(mem_id);
			vo.setPw(mem_pw);
			vo.setLogin_type("k");
			User mvo = boardService.loginCheck(vo);

			System.out.println(mvo);
			session.setAttribute("type","k");
			session.setAttribute("mvo", mvo);
			return "redirect:/";

	}
}}
