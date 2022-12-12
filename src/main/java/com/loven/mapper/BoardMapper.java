package com.loven.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.loven.entity.BlindVO;
import com.loven.entity.Company;
import com.loven.entity.Criteria;
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
	public List<BlindVO> blindList(Criteria cri);
	public List<BlindVO> ablindList(Criteria cri);
	public void blindInsert(BlindVO vo);
	public BlindVO blindView(int seq);
	public void blindUpdate(BlindVO vo);
	public void blindDelete(int seq);
	public boolean plusCnt(int seq);
	public int cntBlind(Criteria cri);
	public int cntAblind(Criteria cri);
	public List<BlindVO> searchTitle(HashMap<String, Object> map);
	public List<BlindVO> searchContent(HashMap<String, Object> map);
	public int cntSearch1(String search);
	public int cntSearch2(String search);
}
