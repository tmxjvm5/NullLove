package com.loven.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.engine.AttributeName;

import com.loven.entity.User;
import com.loven.service.AdminService;

@Controller
public class AdminController {
	@Autowired
	AdminService adminService;
	
	// 전체 유저 리스트
	@GetMapping("/userList.do")
	public String getList(Model model){
		System.out.println("유저 리스트 요청");
		List<User> list = adminService.userList();
		model.addAttribute("list", list);
		
		return "adminpage";
		
	}
	// 유저 삭제
	@GetMapping("/userList/delete/{id}")
	public String deleteUser(@PathVariable("id") String id, Model model) {
		System.out.println(id);
		adminService.userDelete(id);
		List<User> list = adminService.userList();
		model.addAttribute("list", list);
		
		return "redirect:/userList.do";
	}
	
	// 옵션별 유저 검색
	@GetMapping("/userSearch.do")
	public String searchList(@RequestParam("search") String search, @RequestParam("option") String option,Model model) {
		System.out.println("유저 검색");
		List<User> list = null;
		
		if(search == "") {
			list = adminService.userList();
		}else {
			if(option.equals("1")) {
				list = adminService.searchList1(search);
			}
			else if(option.equals("2")) {
				list = adminService.searchList2(search);
			}else {
				list = adminService.searchList3(search);
			}
		}
		model.addAttribute("list", list);
		
		return "adminsearch";
		
	}
	
}
