package com.loven.jy.service;

import java.util.List;

import com.loven.jy.entity.Boast_cmt;


public interface ImgCmtService {

	public void imgCmtInsert(Boast_cmt cmt) ;

	public void imgCmtUpdate(Boast_cmt cmt);

	public void imgCmtDelete(int cmt_seq);
	public List<Boast_cmt> imgCmtList(int seq);

}
