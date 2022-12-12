package com.loven.controller;

import com.loven.entity.BlindVO;
import com.loven.entity.Comment;
import com.loven.entity.PostVO;
import com.loven.entity.User;
import com.loven.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MypageController {

	@Autowired
	MypageService mypageService;

	// 회원탈퇴
	@GetMapping("/mypage/delete/{id}")
	public String deleteUser(@PathVariable("id") String id, Model model) {
		System.out.println(id);
		mypageService.userDelete(id);
		return "redirect:/logout.do";
	}

	// 마이페이지 게시글 리스트
	@RequestMapping("/mypageList")
	public String postList(HttpSession session, Model model) {

		User vo = (User) session.getAttribute("mvo");
		// System.out.println(vo);
		if (vo != null) {
			String id = vo.getId();
			// System.out.println(id);
			List<BlindVO> list = mypageService.postList(id);
			// System.out.println(list);
			model.addAttribute("list", list);
		}

		return "mypageList";

	}

	// 마이페이지 유저 수정
	@RequestMapping(value = "/update_my_page")
	public String update_my_page(@ModelAttribute("umpform_per") User vo, HttpSession session) {
		System.out.println("session : " + session.getAttribute("vo"));
		System.out.println("update : " + vo);

		User sessionVO = (User) session.getAttribute("vo");

		if (vo.getPw() == "" || vo.getPw() == null || vo.getPw().length() == 0) {
			vo.setPw(sessionVO.getPw());
		}

		if (vo.getEmail() == "" || vo.getEmail() == null || vo.getEmail().length() == 0) {
			vo.setEmail(sessionVO.getEmail());
		}
		/*
		 * if (vo.getNick() == "" || vo.getNick() == null || vo.getNick().length() == 0)
		 * { vo.setNick(sessionVO.getNick()); }
		 */
		System.out.println("update : " + vo);
		mypageService.update_vo(vo);

		return "redirect:/logout.do";
	}

	// 기업회원 마이페이지폼
	@RequestMapping("/mypage_com")
	public String mypage_com() {
		return "mypage_com";
	}

	// 마이페이지 이동
	@RequestMapping("/myPage.do")
	public String myPage() {

		return "myPage";
	}

	// 마이페이지 내가 쓴글 게시글 삭제
	@ResponseBody
	@GetMapping("/mypageDelete")
	public String mypageDelete(HttpSession session, String seq) {
		System.out.println(seq);
		User vo = (User) session.getAttribute("mvo");
		System.out.println(vo);
		if (vo != null) {
			mypageService.mypageDelete(seq);
		}

		return "redirect:/mypageList";
	}

	// 마이페이지 내가쓴 댓글 리스트
	@RequestMapping("/mypagecmtList")
	public String cmtList(HttpSession session, Model model) {
		User vo = (User) session.getAttribute("mvo");

		/* System.out.println(vo); */
		if (vo != null) {
			String id = vo.getId();

			/* System.out.println(id); */
			List<Comment> list = mypageService.cmtList(id);
//			System.out.println(list);
			model.addAttribute("list", list);
		}

		return "mypagecmtList";

	}
	// 마이페이지 내가 쓴 댓글 삭제
		@ResponseBody
		@GetMapping("/mypagecmtDelete")
		public String mypagecmtDelete(HttpSession session, String cmt_seq) {
			System.out.println("내가쓴댓글");
			System.out.println(cmt_seq);
			User vo = (User) session.getAttribute("mvo");
			/* System.out.println(vo); */
			if (vo != null) {
				mypageService.mypagecmtDelete(cmt_seq);
			}

			return "redirect:/mypagecmtList";
		}
		@RequestMapping("/mail")
		public String mail() {
			return "mail";
		}
}
