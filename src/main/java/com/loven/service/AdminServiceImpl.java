package com.loven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loven.entity.User;
import com.loven.mapper.MemberMapper;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	MemberMapper mapper;
	@Override
	public List<User> userList() {
		List<User> list = mapper.userList();
		return list;
	}
	
	@Override
	public void userDelete(String id) {
		mapper.userDelete(id);
		
	}

	@Override
	public void userPostDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userCommentDelete() {
		// TODO Auto-generated method stub
		
	}

	

}
