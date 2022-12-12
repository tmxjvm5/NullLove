package com.loven.controller;

import com.loven.entity.Comment;
import com.loven.service.CommentService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

//    @RequestMapping("/blindView")// 댓글리스트
//    @ResponseBody
//    private List<Comment> Comment(@ModelAttribute Comment comment) throws Exception{
//
//        return commentService.commentListService();
//    }

    @RequestMapping("/cmt_insert") // 댓글작성
    private String CommentInsert(@ModelAttribute Comment comment) throws Exception {

        commentService.commentInsertService(comment);

        return "redirect:/blindView?seq="+comment.getSeq();

    }

    @RequestMapping("/cmt_update") // 댓글수정
    private String CommentUpdate(@ModelAttribute Comment comment) throws Exception{

        commentService.commentUpdateService(comment);

        return "redirect:/blindView?seq="+comment.getSeq();
    }

    @RequestMapping("/cmt_delete") // 댓글삭제
    private String CommentDelete(@ModelAttribute Comment comment) throws Exception{
        System.out.println("comment = " + comment);
        commentService.commentDeleteService(comment.getCmt_seq());
        System.out.println("comment = " + comment);
        return "redirect:/blindView?seq="+comment.getSeq();
    }

}
