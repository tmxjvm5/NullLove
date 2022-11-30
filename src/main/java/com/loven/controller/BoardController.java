package com.loven.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loven.mapper.BoardMapper;
import com.loven.service.BoardService;


@Controller
public class BoardController {
	@Autowired
	BoardService service;
	
	}
	
	


