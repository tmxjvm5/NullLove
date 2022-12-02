package com.loven.service;

import java.util.List;

import com.loven.entity.User;

public interface AdminService {
	
	public List<User> userList(); // 유저 리스트 불러오기
	public void userDelete(String id); // 유저 삭제
	
	// +) 유저 게시글 및 댓글 불러오기
	public void userPostDelete(); //유저 게시글 삭제
	public void userCommentDelete(); // 유저 댓글 삭제
	

}
