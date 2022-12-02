package com.loven.service;

import com.loven.entity.Comment;
import com.loven.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    public List<Comment> commentListService() throws Exception{

        return commentMapper.commentList();
    }

    public int commentInsertService(Comment comment) throws Exception{

        return commentMapper.commentInsert(comment);
    }

    public int commentUpdateService(Comment comment) throws Exception{

        return commentMapper.commentUpdate(comment);
    }

    public int commentDeleteService(int cmt_seq) throws Exception{

        return commentMapper.commentDelete(cmt_seq);
    }
}
