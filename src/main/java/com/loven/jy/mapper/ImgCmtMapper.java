package com.loven.jy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.loven.jy.entity.Boast_cmt;

@Mapper
public interface ImgCmtMapper {
	public void imgCmtInsert(Boast_cmt cmt);

	public void imgCmtUpdate(Boast_cmt cmt);

	public void imgCmtDelete(int cmt_seq);
	
	public List<Boast_cmt> imgCmtList(int seq);

}
