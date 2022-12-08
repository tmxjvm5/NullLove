package com.loven.jy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.loven.entity.BlindVO;
import com.loven.jy.entity.Boast;
import com.loven.jy.mapper.ImgBoardMapper;
import com.loven.jy.service.ImgBoardService;

@Controller
public class ImgBoardCtrl {
	@Autowired
	ImgBoardMapper mapper;
	
	@Autowired
	ImgBoardService service;
	
	@RequestMapping("/imgBoardList")
	public String list(Model model) {
		List<Boast> list=mapper.getLists();
		model.addAttribute("list",list);
		return "jy/imgBoardList"; //templates / board / list.html
	}
	// 글쓰기 폼
	@RequestMapping("/imgBoardForm")
	public String imgBoardForm() {
		return "jy/imgBoardWrite";
	}
	//글쓰기
	@RequestMapping("/imgBoardWrite")
	public String blindWrite(Boast vo) {
		System.out.println(vo);
		service.imgBoardInsert(vo);
		return "redirect:imgBoardList";
	}
	//상세view
	@RequestMapping("/imgBoardView")
	public String imgBoardView(@RequestParam("seq") int seq, Model model) {
		Boast vo = service.imgBoardView(seq);
		model.addAttribute("vo",vo);
		service.cntPlus(seq);
		return "jy/imgBoardView";
	}
	// 게시판 수정 폼
	@GetMapping("/imgBoardUpdateForm/{seq}")
	public String imgBoardUpdateForm(Model model,@PathVariable int seq) {
		Boast vo = service.imgBoardView(seq);
		model.addAttribute("vo", vo);
		return "jy/imgBoardUpdate";
	}
	
	//게시판 수정
	@PostMapping("/imgBoardUpdate")
	public String imgBoardUpdate(Boast vo) {
		System.out.println(vo);
		service.imgBoardUpdate(vo);
		
		return "redirect:imgBoardList";
	}
	
//	@PostMapping("/imgBoardUpdate")
//	ModelAndView imgBoardUpdate(Boast vo, ModelAndView mav) {
//		service.imgBoardUpdate(vo);
//		
//		mav.addObject("data", new Message("수정되었습니다.", "/imgBoardList"));
//		mav.setViewName("Message");
//		
//		return mav;
//	}
	// 게시판 삭제
	@GetMapping("/imgBoardDelete/{seq}")
	public String imgBoardDelete(@PathVariable int seq) {
		service.imgBoardDelete(seq);
		return "redirect:/imgBoardList";
	}
}
