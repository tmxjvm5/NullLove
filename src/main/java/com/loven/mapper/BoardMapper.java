package com.loven.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.loven.entity.BlindVO;
import com.loven.entity.Company;
import com.loven.entity.User;

//Mybatis
@Mapper
public interface BoardMapper {
	public void joinInsert(User vo);
	public User loginCheck(User vo);
	public User loginAdmin(User vo);
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
