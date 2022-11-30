package com.loven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loven.entity.Company;
import com.loven.entity.User;
import com.loven.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardMapper mapper;
	// 일반회원가입
	@Override
	public void joinInsert(User vo) {
		mapper.joinInsert(vo);
		
	}
	// 일반 로그인
	@Override
	public User loginCheck(User vo) {
		User mvo = mapper.loginCheck(vo);
		return mvo;
	}
	// 아이디중복체크
	@Override
	public int idCheck(String id) {
		int cnt = mapper.idCheck(id);
		System.out.println("cnt: " + cnt);
		return cnt;
	}
	//기업 회원가입
	@Override
	public void cInsert(Company vo) {
		
		mapper.cInsert(vo);
		
	}
	@Override
	public Company companyCheck(User vo) {
		return mapper.companyCheck(vo);
		
	}
	@Override
	public void memberUpdate(User vo) {
		mapper.memberUpdate(vo);
		
	}
	
}
