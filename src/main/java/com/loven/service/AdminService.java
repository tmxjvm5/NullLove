package com.loven.service;

import java.util.List;

import com.loven.entity.Criteria;
import com.loven.entity.User;

public interface AdminService {
	
	public List<User> userList(Criteria cri); // 유저 리스트 불러오기
	public int countUser(Criteria cri);
	public List<User> searchList1(String id); // 유저 검색(아이디)
	public List<User> searchList2(String email); // 유저 검색(이메일)
	public List<User> searchList3(String company_name); // 유저 검색(기업명)
	
	public void userDelete(String id); // 유저 삭제
	
	public void userPostDelete(String id); //유저 게시글 삭제
	public void userCommentDelete(String id); // 유저 댓글 삭제
	

}
