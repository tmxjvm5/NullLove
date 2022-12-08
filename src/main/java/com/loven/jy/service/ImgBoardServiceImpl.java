package com.loven.jy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loven.entity.BlindVO;
import com.loven.jy.entity.Boast;
import com.loven.jy.mapper.ImgBoardMapper;

@Service
public class ImgBoardServiceImpl implements ImgBoardService{
	@Autowired
	ImgBoardMapper mapper;

	@Override
	public List<Boast> getLists() {
		List<Boast> list=mapper.getLists();
		return list;
	}

	@Override
	public void imgBoardInsert(Boast vo) {
		mapper.imgBoardInsert(vo);
	}

	@Override
	public Boast imgBoardView(int seq) {
		Boast vo = mapper.imgBoardView(seq);
		return vo;
	}

	@Override
	public boolean cntPlus(int seq) {
		return mapper.cntPlus(seq);
	}

	@Override
	public void imgBoardUpdate(Boast vo) {
		mapper.imgBoardUpdate(vo);
	}

	@Override
	public void imgBoardDelete(int seq) {
		mapper.imgBoardDelete(seq);
	}
	
}	
