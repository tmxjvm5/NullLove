package com.loven.jy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.loven.entity.BlindVO;
import com.loven.jy.entity.Boast;

@Mapper
public interface ImgBoardMapper {
	public List<Boast> getLists();
	public void imgBoardInsert(Boast vo);
	public Boast imgBoardView(int seq);
	public boolean cntPlus(int seq);
	public void imgBoardUpdate(Boast vo);
	public void imgBoardDelete(int seq);
}
