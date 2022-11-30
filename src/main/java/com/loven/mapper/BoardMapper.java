package com.loven.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.loven.entity.Company;
import com.loven.entity.User;

//Mybatis
@Mapper
public interface BoardMapper {
	public void joinInsert(User vo);
	public User loginCheck(User vo);
	public int idCheck(String id);
	public void cInsert(Company vo);
	public Company companyCheck(User vo);
	public void memberUpdate(User vo);
	
 
}
