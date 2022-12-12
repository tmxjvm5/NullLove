package com.loven.jy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loven.jy.entity.Boast_cmt;
import com.loven.jy.service.ImgCmtService;

@Controller
public class ImgCmtCtrl {
	
	@Autowired
	ImgCmtService service;
	
	
	@RequestMapping("/imgCmtInsert") // 댓글작성
    private String imgCmtInsert(@ModelAttribute Boast_cmt cmt) throws Exception {

		System.out.println(cmt);
        service.imgCmtInsert(cmt);

        return "redirect:/imgBoardView?seq="+cmt.getBoast_seq();

    }
	
	@RequestMapping("/imgCmtUpdate") // 댓글수정
	@ResponseBody
    private void CommentUpdate(@ModelAttribute Boast_cmt cmt) throws Exception{

		service.imgCmtUpdate(cmt);

    }
	
	
    @RequestMapping("/imgCmtDelete") // 댓글삭제
    private String CommentDelete(@ModelAttribute Boast_cmt cmt) throws Exception{
        service.imgCmtDelete(cmt.getCmt_seq());
        
        return "redirect:/imgBoardView?seq="+cmt.getBoast_seq();
    }
	
}
