package com.loven.controller;

import java.util.List;

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
	// 게시판 리스트
	@RequestMapping("/blindList")
	public String blindList(Model model) {
		List<BlindVO> list = service.blindList();
		model.addAttribute("list", list);
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
		model.addAttribute("vo", vo);
		//조회수 증가
		service.plusCnt(seq);
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
	@GetMapping("/blindDelete/{seq}")
	public String blindDelete(@PathVariable int seq) {
		service.blindDelete(seq);
		return "redirect:/blindList";
}
	
	
}

