package com.loven.controller;

import com.loven.entity.Comment;
import com.loven.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping("/insert") // 댓글작성
    @ResponseBody
    private int CommentInsert(@ModelAttribute Comment comment) throws Exception {

        return commentService.commentInsertService(comment);

    }

    @RequestMapping("/update") // 댓글수정
    @ResponseBody
    private int CommentUpdate(@ModelAttribute Comment comment) throws Exception{

        return commentService.commentUpdateService(comment);
    }

    @RequestMapping("/delete/{cmt_seq}") // 댓글삭제
    @ResponseBody
    private int CommentDelete(@PathVariable int cmt_seq) throws Exception{

        return commentService.commentDeleteService(cmt_seq);
    }

}
