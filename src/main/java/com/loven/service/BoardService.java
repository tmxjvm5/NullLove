package com.loven.service;

import java.util.List;

import com.loven.entity.BlindVO;
import com.loven.entity.Company;
import com.loven.entity.User;

public interface BoardService {

	public void joinInsert(User vo);

	public User loginCheck(User vo);
	
	public User loginAdmin(User vo); // 관리자 로그인

	public int idCheck(String id);

	public void cInsert(Company vo);

	public Company companyCheck(User vo);
	
	public void memberUpdate(User vo);

	public List<BlindVO> blindList();

	public List<BlindVO> ablindList();

	public void blindInsert(BlindVO vo);

	public BlindVO blindView(int seq);

	public void blindUpdate(BlindVO vo);

	public void blindDelete(int seq);

	public boolean plusCnt(int seq);


}
