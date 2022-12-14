package com.loven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loven.entity.Criteria;
import com.loven.entity.User;
import com.loven.mapper.MemberMapper;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	MemberMapper mapper;
	
	@Override
	public List<User> userList(Criteria cri) {
		List<User> list = mapper.userList(cri);
		return list;
	}
	
	@Override
	public List<User> searchList1(String id) {
		List<User> list = mapper.searchList1(id);
		return list;
	}
	
	@Override
	public List<User> searchList2(String email) {
		List<User> list = mapper.searchList2(email);
		return list;
	}

	@Override
	public List<User> searchList3(String company_name) {
		List<User> list = mapper.searchList3(company_name);
		return list;
	}
	
	@Override
	public void userDelete(String id) {
		mapper.disableFk();
		mapper.userDelete(id);
		mapper.enableFk();
	}

	@Override
	public void userPostDelete(String id) {
		mapper.disableFk();
		mapper.userPostDelete(id);
		mapper.enableFk();
		
	}

	@Override
	public void userCommentDelete(String id) {
		mapper.disableFk();
		mapper.userCommentDelete(id);
		mapper.enableFk();
		
	}

	@Override
	public int countUser(Criteria cri) {
		int cnt = mapper.countUser(cri);
		return cnt;
	}

	

	

	

}
