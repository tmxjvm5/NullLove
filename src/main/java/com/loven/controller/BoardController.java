package com.loven.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.loven.entity.Comment;
import com.loven.entity.Criteria;
import com.loven.entity.PageMaker;
import com.loven.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loven.entity.BlindVO;
import com.loven.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	BoardService service;

	@Autowired
	CommentService commentService;
	// 게시판 리스트
	@RequestMapping("/blindList{page}")
	public String blindList(Model model, Integer page) {
		if(page==null) {
			page=1;
		}
		Criteria cri = new Criteria(page);
		List<BlindVO> list = service.blindList(cri);
		model.addAttribute("list", list);
		List<BlindVO> alist = service.ablindList(cri); // 공지사항(관리자)
		model.addAttribute("alist", alist);
		
		PageMaker pageMaker = new PageMaker(cri,service.cntBlind(cri));
		model.addAttribute("pageMaker", pageMaker);
		
		return "blindList";

	}
	// 글쓰기 폼
	@RequestMapping("/blindForm")
	public String blindForm() {

		return "blindWrite";
	}
 // 게시판 글쓰기
	@RequestMapping("/blindWrite")
	public String blindWrite(BlindVO vo) {
		System.out.println(vo);
		service.blindInsert(vo);
		return "redirect:/blindList";

	}
		// 게시판 상세보기
	@GetMapping("/blindView")
	public String blindView(@RequestParam("seq") int seq, Model model) {
		BlindVO vo = service.blindView(seq);
		List<Comment> cmt = commentService.commentListService(seq);
		model.addAttribute("vo", vo);
		model.addAttribute("cmt", cmt);
		//조회수 증가
		service.plusCnt(seq);
		System.out.println(vo);
		return "blindView";
	}
	//게시판 수정
	@PostMapping("/blindUpdate")
	public String blindUpdate(BlindVO vo) {
		System.out.println(vo);
		service.blindUpdate(vo);
		return "redirect:/blindList";
	}
	// 게시판 수정 폼
	@GetMapping("/blindUpdateForm/{seq}")
	public String blindUpdateForm(Model model,@PathVariable int seq) {
		BlindVO vo = service.blindView(seq);
		model.addAttribute("vo", vo);
		return "blindUpdate";
	}
	// 게시판 삭제
	@GetMapping("blindDelete/{seq}")
	public String blindDelete(@PathVariable int seq) {
		service.blindDelete(seq);
		return "redirect:/blindList";
}
	
	// 옵션별 게시글 검색
	@GetMapping("/blindSearch{page}")
	public String searchList(@RequestParam("search") String search, @RequestParam("option") String option, Model model, Integer page) {
		if(page==null) {
			page=1;
		}
		Criteria cri = new Criteria(page);
		if(search==null) {
			search="";
		}
		if(option==null) {
			option = "1";
		}
		
		cri.setOption(option);
		cri.setSearch(search);
		
		List<BlindVO> list = null;
		List<BlindVO> alist = service.ablindList(cri); // 공지사항(관리자)
		model.addAttribute("alist", alist);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("cri", cri);
		PageMaker pageMaker;
		if(search == "") {
			list = service.blindList(cri);
			model.addAttribute("list", list);
			return "redirect:/blindList";
		}else {
			if(option.equals("1")) {
				list = service.searchTitle(map);
				pageMaker = new PageMaker(cri,service.cntSearch1(search));
			}else {
				list = service.searchContent(map);
				pageMaker = new PageMaker(cri,service.cntSearch2(search));
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		
		
		return "blindSearch";
		
	}

	
}

