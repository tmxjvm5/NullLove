package com.loven.jy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loven.jy.entity.Boast_cmt;
import com.loven.jy.mapper.ImgCmtMapper;

@Service
public class ImgCmtServiceImpl implements ImgCmtService  {
	
	@Autowired
	ImgCmtMapper mapper;

	@Override
	public void imgCmtInsert(Boast_cmt cmt){
		mapper.imgCmtInsert(cmt);
	}

	@Override
	public void imgCmtUpdate(Boast_cmt cmt) {
		mapper.imgCmtUpdate(cmt);
	}

	@Override
	public void imgCmtDelete(int cmt_seq) {
		mapper.imgCmtDelete(cmt_seq);
	}
	@Override
	public List<Boast_cmt> imgCmtList(int seq) {
		return mapper.imgCmtList(seq);
	}



}
