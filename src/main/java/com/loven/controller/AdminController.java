package com.loven.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loven.entity.User;
import com.loven.service.AdminService;

@Controller
public class AdminController {
	@Autowired
	AdminService adminService;
	
	@GetMapping("/userList.do")
	public String getList(Model model){
		System.out.println("유저 리스트 요청");
		List<User> list = adminService.userList();
		model.addAttribute("list", list);
		
		return "adminpage";
		
	}
	// 삭제 미완성
	@GetMapping("/userList/delete/{id}")
	public String deleteUser(@PathVariable("id") String id) {
		adminService.userDelete(id);
		System.out.println(id);
		
		return "redirect:/adminpage";
	}
	
	
}
