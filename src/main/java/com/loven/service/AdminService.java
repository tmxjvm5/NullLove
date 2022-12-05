package com.loven.service;

import java.util.List;

import com.loven.entity.User;

public interface AdminService {
	
	public List<User> userList(); // 유저 리스트 불러오기
	public List<User> searchList1(String id); // 유저 검색(아이디)
	public List<User> searchList2(String email); // 유저 검색(이메일)
	public List<User> searchList3(String company_name); // 유저 검색(기업명)
	
	public void userDelete(String id); // 유저 삭제
	
	// 미구현
	public void userPostDelete(); //유저 게시글 삭제
	public void userCommentDelete(); // 유저 댓글 삭제
	

}
