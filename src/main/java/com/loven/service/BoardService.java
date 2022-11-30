package com.loven.service;

import com.loven.entity.Company;
import com.loven.entity.User;

public interface BoardService {

	public void joinInsert(User vo);

	public User loginCheck(User vo);

	public int idCheck(String id);

	public void cInsert(Company vo);

	public Company companyCheck(User vo);
	
	public void memberUpdate(User vo);

}
