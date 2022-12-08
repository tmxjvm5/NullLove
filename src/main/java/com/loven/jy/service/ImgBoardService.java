package com.loven.jy.service;

import java.util.List;

import com.loven.jy.entity.Boast;

public interface ImgBoardService {
	public List<Boast> getLists();
	public void imgBoardInsert(Boast vo);
	public Boast imgBoardView(int seq);
	public boolean cntPlus(int seq);
	public void imgBoardUpdate(Boast vo);
	public void imgBoardDelete(int seq);
}
